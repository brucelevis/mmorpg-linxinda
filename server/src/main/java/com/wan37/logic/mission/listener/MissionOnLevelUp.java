package com.wan37.logic.mission.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.LevelUpEvent;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.service.accept.MissionAccepter;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务监听升级事件
 */
@Service
class MissionOnLevelUp implements GeneralEventListener<LevelUpEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    @Autowired
    private MissionAccepter missionAccepter;

    @Override
    public void execute(LevelUpEvent levelUpEvent) {
        Player player = levelUpEvent.getPlayer();

        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.MISSION_TYPE_2.getId()))
                .filter(m -> !m.canComplete())
                .forEach(m -> missionCompleteChecker.check(player, m));

        // 自动接取任务
        missionCfgLoader.loads().stream()
                .filter(m -> m.getLevel() == player.getLevel())
                .filter(MissionCfg::isAutoAccept)
                .forEach(c -> acceptImpl(player, c));
    }

    private void acceptImpl(Player player, MissionCfg missionCfg) {
        Integer preId = missionCfg.getPreId();
        if (preId != null && !player.getMission().hadCompleted(preId)) {
            return;
        }

        missionAccepter.accept(player, missionCfg);
    }
}
