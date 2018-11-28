package com.wan37.logic.attack.fighting.before;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.EquipPartEnum;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.ISkill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 攻击出手前处理
 */
@Service
public class FightingBeforeChecker {

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    public boolean check(FightingUnit attacker, FightingUnit target, ISkill skill) {
        if (isPlayer(attacker)) {
            // 攻击者是玩家需要特殊处理
            Player player = (Player) attacker;

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
        }

        if (!Objects.equals(attacker.getSceneId(), target.getSceneId())) {
            return throwIfIsPlayer(attacker, "不在同一个地图，无法攻击");
        }

        if (!attacker.isAlive()) {
            return throwIfIsPlayer(attacker, "死亡状态无法攻击");
        }

        if (skill == null) {
            return throwIfIsPlayer(target, "找不到目标技能");
        }

        // 检查技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        long interval = now - skill.getLastUseTime();
        if (interval < skill.getCdInterval()) {
            return throwIfIsPlayer(attacker, "技能cd中，无法使用");
        }

        // 检查蓝量
        int costMp = skill.getCostMp();
        if (attacker.getMp() < costMp) {
            return throwIfIsPlayer(attacker, "蓝量不足，无法攻击");
        }

        //TODO: 检查攻击者 BUFF异常状态，如眩晕，封印，击飞等

        if (!target.isAlive()) {
            return throwIfIsPlayer(attacker, "目标死亡状态，无法攻击");
        }

        //TODO: 检查目标 状态，如无法被攻击，选取等

        return true;
    }

    private boolean throwIfIsPlayer(FightingUnit unit, String msg) {
        if (isPlayer(unit)) {
            throw new GeneralErrorExecption(msg);
        }

        return false;
    }

    private boolean isPlayer(FightingUnit unit) {
        return unit instanceof Player;
    }
}
