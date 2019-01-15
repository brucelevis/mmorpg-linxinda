package com.wan37.logic.mission.listener;

import com.wan37.config.ConfigLoader;
import com.wan37.event.GeneralEventListener;
import com.wan37.event.event.ItemAddEvent;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务监听加物品事件
 * FIXME: 这里应该再分一层是加装备的，MissionOnEquipAdd
 */
@Service
class MissionOnItemAdd implements GeneralEventListener<ItemAddEvent> {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(ItemAddEvent itemAddEvent) {
        if (!isEquip(itemAddEvent.getPropsCfg().getId())) {
            // 不是装备
            return;
        }

        Player player = itemAddEvent.getPlayer();
        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.GET_PERFECT_EQUIP.getId()))
                .filter(m -> !m.canComplete())
                .forEach(m -> missionCompleteChecker.check(player, m));
    }

    private boolean isEquip(Integer cfgId) {
        return configLoader.load(EquipCfg.class, cfgId).isPresent();
    }
}
