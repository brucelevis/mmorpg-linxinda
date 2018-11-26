package com.wan37.logic.attack.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.buff.BuffTargetEnum;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.config.BuffCfg;
import com.wan37.logic.buff.config.BuffCfgLoader;
import com.wan37.logic.equipment.EquipPartEnum;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.die.MonsterDieHandler;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.service.PlayerBuffAdder;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.skill.config.SkillBuffCfg;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.config.SkillCfgLoader;
import com.wan37.logic.skill.database.PSkillDb;
import com.wan37.logic.skill.database.PlayerSkillDb;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * FIXME: 整个战斗写的很恶心
 */
@Service
public class AttackPlayerToMonsterExec {

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private SkillCfgLoader skillCfgLoader;

    @Autowired
    private MonsterEncoder monsterEncoder;

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    @Autowired
    private BuffCfgLoader buffCfgLoader;

    @Autowired
    private IBuff.Factory buffFactory;

    @Autowired
    private PlayerBuffAdder playerBuffAdder;

    @Autowired
    private MonsterDieHandler monsterDieHandler;

    public void exec(Player player, Integer skillId, Long monsterUid) {
        PlayerDb playerDb = player.getPlayerDb();
        EquipDb equipDb = playerDb.getEquipDb();
        ItemDb equipItem = equipDb.getItems().get(EquipPartEnum.PART_1.getId());
        if (equipItem == null) {
            throw new GeneralErrorExecption("未佩戴武器，无法攻击");
        }

        EquipExtraDb equipExtraDb = equipExtraDbGetter.get(equipItem.getExtraDb());
        if (equipExtraDb.getDurabilityv() < 20) {
            //FIXME: 写死攻击时武器耐久度要求
            throw new GeneralErrorExecption("武器耐久度过低，请及时修理");
        }

        PlayerSkillDb playerSkillDb = playerDb.getPlayerSkillDb();
        PSkillDb skillDb = playerSkillDb.getSkills().get(skillId);
        if (skillDb == null) {
            throw new GeneralErrorExecption("找不到目标技能");
        }

        SkillCfg skillCfg = skillCfgLoader.load(skillDb.getCfgId())
                .orElseThrow(() -> new RuntimeException("技能配置表出错"));

        // 检查技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        long interval = now - skillDb.getLastUseTime();
        int skillCd = skillCfg.getCd(skillDb.getLevel());
        if (interval < skillCd) {
            throw new GeneralErrorExecption("技能cd中，无法使用");
        }

        // 检查蓝量
        int costMp = skillCfg.getCostMp(skillDb.getLevel());
        if (playerDb.getMp() < costMp) {
            throw new GeneralErrorExecption("蓝量不足，无法攻击");
        }

        Scene scene = sceneGlobalManager.getScene(player.getSceneId());
        Monster monster = scene.getMonsters().stream()
                .filter(m -> Objects.equals(m.getUid(), monsterUid))
                .findAny()
                .orElse(null);

        if (monster == null) {
            throw new GeneralErrorExecption("目标怪物不存在");
        }

        if (!monster.isAlive()) {
            throw new GeneralErrorExecption("怪物死亡状态，不可攻击");
        }

        // 扣蓝
        playerDb.setMp(playerDb.getMp() - costMp);

        //FIXME: 写死减少装备耐久度1
        equipExtraDb.setDurabilityv(equipExtraDb.getDurabilityv() - 1);

        // 设置技能cd
        skillDb.setLastUseTime(now);

        // 标记打人的人
        monster.setLastAttackId(player.getUid());

        /**
         * FIXME: 攻击先简单写死做
         * 1.先算出人物的基础面板总攻击，然后计算出技能加成后能打出的伤害 A1
         * 2.计算出怪物的防御力抵挡 D1
         * 3.判断怪物身上有没伤害护盾buff D2，然后处理护盾，判断是否打破。更新。。
         * 4.计算扣血伤害，然后扣血
         * 5.击中后判断技能会不会触发Buff
         */
        PlayerStrengthDb playerStrengthDb = playerDb.getPlayerStrengthDb();
        double baseVal = playerStrengthDb.getBaseAttackVal();
        double skillPercent = skillCfg.getDemage(skillDb.getLevel());
        long demage = Math.round(baseVal * skillPercent);

        //FIXME: 恶心代码。
        if (demage <= monster.getBaseDefenseVal()) {
            demage = 0;
        } else {
            demage -= monster.getBaseDefenseVal();
        }

        long curHp = monster.getHp();
        if (curHp > demage) {
            // 怪物没死
            monster.setHp(curHp - demage);

            // 打印
            String msg = String.format("你用%s击中%s，造成伤害%s，消耗%smp", skillCfg.getName(), monster.getName(), demage, costMp);
            player.syncClient(msg);
        } else {
            // 怪物死了
            String msg = String.format("你用%s击杀了%s，造成伤害%s，消耗%smp", skillCfg.getName(), monster.getName(), demage, costMp);
            player.syncClient(msg);

            monsterDieHandler.handle(monster, now);
        }

        // 概率触发Buff
        skillCfg.getBuffs().forEach(c -> randBuff(player, monster, c));

        // 通知场景玩家怪物状态更新
        String monsterUpdate = "怪物状态更新推送|" + monsterEncoder.encode(monster);
        scene.getPlayers().forEach(p -> p.syncClient(monsterUpdate));
    }

    private void randBuff(Player player, Monster monster, SkillBuffCfg cfg) {
        if (!RandomUtil.isHit(cfg.getProbability())) {
            return;
        }

        BuffCfg buffCfg = buffCfgLoader.load(cfg.getId()).orElse(null);
        if (buffCfg == null) {
            return;
        }

        IBuff buff = buffFactory.create(buffCfg);
        if (Objects.equals(buff.getTarget(), BuffTargetEnum.BUFF_TARGET_1.getId())) {
            playerBuffAdder.add(player, buff);
        } else {
            if (!monster.isAlive()) {
                return;
            }

            // 去重
            List<IBuff> buffs = monster.getBuffs();
            buffs.stream().filter(b -> Objects.equals(b.getId(), buff.getId()))
                    .findAny()
                    .ifPresent(buffs::remove);

            buffs.add(buff);
        }

        String msg = String.format("你触发了%s", buff.getName());
        player.syncClient(msg);
    }
}
