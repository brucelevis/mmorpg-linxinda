package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipCfgLoader;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_6
 */
@Service
class MissionCompleteCheckBehav6 implements MissionCompleteCheckBehavior {

    @Autowired
    private EquipCfgLoader equipCfgLoader;

    @Override
    public void behave(MissionCompleteCheckContext context) {
        Player player = context.getPlayer();
        EquipDb equipDb = player.getPlayerDb().getEquipDb();

        // 身上穿戴的所有装备等级和
        int totalEquipLv = equipDb.getItems().values().stream()
                .map(i -> equipCfgLoader.load(i.getCfgId()))
                .map(Optional::get)
                .mapToInt(EquipCfg::getLevel)
                .sum();

        IPlayerMission playerMission = context.getPlayerMission();
        MissionCfg missionCfg = playerMission.getMissionCfg();
        if (totalEquipLv < missionCfg.getArgs()) {
            return;
        }

        context.setResult(true);
    }
}
