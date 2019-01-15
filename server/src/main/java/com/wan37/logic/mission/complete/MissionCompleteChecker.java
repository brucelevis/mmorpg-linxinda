package com.wan37.logic.mission.complete;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.PlayerMission;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务完成检查器
 *
 * @author linda
 */
@Service
public class MissionCompleteChecker {

    @Autowired
    private BehaviorManager behaviorManager;

    @Autowired
    private MissionCompleteHandler missionCompleteHandler;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void check(Player player, PlayerMission playerMission) {
        MissionCfg missionCfg = playerMission.getMissionCfg();

        MissionCompleteCheckBehavior behavior = (MissionCompleteCheckBehavior) behaviorManager.get(MissionCompleteCheckBehavior.class, missionCfg.getType());
        MissionCompleteCheckContext context = new MissionCompleteCheckContext(player, playerMission);
        behavior.behave(context);

        if (!context.isResult()) {
            // 任务没完成
            return;
        }

        playerMission.setCanComplete(true);

        if (playerGlobalManager.isOnline(player.getUid())) {
            String msg = String.format("[%s]任务完成", playerMission.getMissionCfg().getName());
            player.syncClient(msg);

            if (missionCfg.isAutoCommit()) {
                // 任务为自动完成
                missionCompleteHandler.handle(player, playerMission);
            }
        }
    }
}
