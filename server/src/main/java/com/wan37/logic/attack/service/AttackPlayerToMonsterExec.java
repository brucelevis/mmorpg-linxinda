package com.wan37.logic.attack.service;

import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.EquipPartEnum;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.config.SkillCfgLoader;
import com.wan37.logic.skill.database.PSkillDb;
import com.wan37.logic.skill.database.PlayerSkillDb;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import com.wan37.util.DateTimeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AttackPlayerToMonsterExec {

    private static final Logger LOG = Logger.getLogger(AttackPlayerToMonsterExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private SkillCfgLoader skillCfgLoader;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private MonsterEncoder monsterEncoder;

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    public void exec(String channelId, Integer skillId, Long monsterUid) {
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            return;
        }

        PlayerDb playerDb = player.getPlayerDb();
        EquipDb equipDb = playerDb.getEquipDb();
        ItemDb equipItem = equipDb.getItems().get(EquipPartEnum.PART_1.getId());
        if (equipItem == null) {
            player.syncClient("未佩戴武器，无法攻击");
            return;
        }

        EquipExtraDb equipExtraDb = equipExtraDbGetter.get(equipItem.getExtraDb());
        if (equipExtraDb.getDurabilityv() < 20) {
            //FIXME: 写死攻击时武器耐久度要求
            player.syncClient("武器耐久度过低，请及时修理");
            return;
        }

        PlayerSkillDb playerSkillDb = playerDb.getPlayerSkillDb();
        PSkillDb skillDb = playerSkillDb.getSkills().get(skillId);
        if (skillDb == null) {
            LOG.info("找不到目标技能");
            return;
        }

        SkillCfg skillCfg = skillCfgLoader.load(skillDb.getCfgId())
                .orElseThrow(() -> new RuntimeException("技能配置表出错"));

        // 检查技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        long interval = now - skillDb.getLastUseTime();
        int skillCd = skillCfg.getCd(skillDb.getLevel());
        if (interval < skillCd) {
            player.syncClient("技能cd中，无法使用");
            return;
        }

        Scene scene = sceneGlobalManager.getScene(player.getSceneId());
        Monster monster = scene.getMonsters().stream()
                .filter(m -> Objects.equals(m.getUid(), monsterUid))
                .findAny()
                .orElse(null);

        if (monster == null) {
            player.syncClient("目标怪物不存在");
            return;
        }

        if (!monster.isAlive()) {
            player.syncClient("怪物死亡状态，不可攻击");
            return;
        }

        //FIXME: 先简单的根据面板总伤扣血
        PlayerStrengthDb playerStrengthDb = playerDb.getPlayerStrengthDb();
        double baseVal = playerStrengthDb.getBaseVal();
        double skillPercent = skillCfg.getDemage(skillDb.getLevel());
        long demage = Math.round(baseVal * skillPercent);

        // 设置技能cd及推送
        skillDb.setLastUseTime(now);

        //FIXME: 写死装备耐久度
        equipExtraDb.setDurabilityv(equipExtraDb.getDurabilityv() - 1);

        // 持久化
        playerDao.save(playerDb);

        //TODO: 推送技能cd

        long curHp = monster.getHp();
        if (curHp > demage) {
            // 怪物没死
            monster.setHp(curHp - demage);
        } else {
            // 怪物死了
            monster.setHp(0);
            monster.setAlive(false);
            monster.setDeadTime(now);
        }

        // 打印
        String msg = String.format("你用%s击中%s，造成伤害%s", skillCfg.getName(), monster.getName(), demage);
        player.syncClient(msg);

        // 通知场景玩家怪物状态更新
        String monsterUpdate = "怪物状态更新推送|" + monsterEncoder.encode(monster);
        scene.getPlayers().forEach(p -> p.syncClient(monsterUpdate));
    }
}
