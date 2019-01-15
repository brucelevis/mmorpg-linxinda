package com.wan37.logic.mission.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.event.MonsterDieEvent;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.mission.PlayerMission;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务监听怪物死亡事件
 */
@Service
class MissionOnMonsterDie implements GeneralEventListener<MonsterDieEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(MonsterDieEvent monsterDieEvent) {
        Player attacker = monsterDieEvent.getAttacker();
        Monster monster = monsterDieEvent.getMonster();

        attacker.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.KILL_MONSTER.getId()))
                .filter(m -> Objects.equals(m.getMissionCfg().getTargetId(), monster.getMonsterCfg().getId()))
                .filter(m -> !m.canComplete())
                .forEach(m -> completeImpl(attacker, m));
    }

    private void completeImpl(Player player, PlayerMission playerMission) {
        playerMission.setProgress(playerMission.getProgress() + 1);
        missionCompleteChecker.check(player, playerMission);
    }
}
