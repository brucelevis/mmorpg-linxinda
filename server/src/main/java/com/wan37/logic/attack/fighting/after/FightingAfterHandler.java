package com.wan37.logic.attack.fighting.after;

import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.buff.BuffTargetEnum;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.config.BuffCfg;
import com.wan37.logic.buff.config.BuffCfgLoader;
import com.wan37.logic.equipment.EquipPartEnum;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.service.FightingUnitBuffAdder;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.ISkill;
import com.wan37.logic.skill.config.SkillBuffCfg;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class FightingAfterHandler {

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    @Autowired
    private BuffCfgLoader buffCfgLoader;

    @Autowired
    private IBuff.Factory buffFactory;

    @Autowired
    private FightingUnitBuffAdder fightingUnitBuffAdder;

    public void handle(FightingUnit attacker, FightingUnit target, ISkill skill, AbstractScene scene) {
        // 扣蓝
        long mp = attacker.getMp() - skill.getCostMp();
        attacker.setMp(mp < 0 ? 0 : mp);

        if (isPlayer(attacker)) {
            Player player = (Player) attacker;

            PlayerDb playerDb = player.getPlayerDb();
            EquipDb equipDb = playerDb.getEquipDb();
            ItemDb equipItem = equipDb.getItems().get(EquipPartEnum.PART_1.getId());
            if (equipItem != null) {
                //FIXME: 写死减少装备耐久度1
                EquipExtraDb equipExtraDb = equipExtraDbGetter.get(equipItem.getExtraDb());
                equipExtraDb.setDurabilityv(equipExtraDb.getDurabilityv() - 1);
            }
        }

        // 设置技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        skill.setLastUseTime(now);

        // 攻击技能概率触发Buff
        skill.getSkillCfg().getBuffs().forEach(c -> randBuff(attacker, target, c, scene));

        //TODO: 怪物触发被动
    }

    private void randBuff(FightingUnit attacker, FightingUnit target, SkillBuffCfg cfg, AbstractScene scene) {
        if (!RandomUtil.isHit(cfg.getProbability())) {
            // 没触发buff
            return;
        }

        BuffCfg buffCfg = buffCfgLoader.load(cfg.getId()).orElse(null);
        if (buffCfg == null) {
            return;
        }

        IBuff buff = buffFactory.create(buffCfg);
        if (Objects.equals(buff.getTarget(), BuffTargetEnum.BUFF_TARGET_1.getId())) {
            // 对自己施加buff
            fightingUnitBuffAdder.add(attacker, buff);

            String msg = String.format("[%s]触发了[%s]的效果", attacker.getName(), buff.getName());
            scene.getPlayers().forEach(p -> p.syncClient(msg));
        } else {
            // 对目标施加buff
            if (target.isAlive()) {
                fightingUnitBuffAdder.add(target, buff);

                String msg = String.format("[%s]对[%s]施加了[%s]的效果", attacker.getName(), target.getName(), buff.getName());
                scene.getPlayers().forEach(p -> p.syncClient(msg));
            }
        }
    }

    private boolean isPlayer(FightingUnit unit) {
        return unit instanceof Player;
    }
}
