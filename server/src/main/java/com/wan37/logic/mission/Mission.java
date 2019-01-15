package com.wan37.logic.mission;

import com.wan37.logic.mission.database.MissionDb;

import java.util.List;

/**
 * 玩家任务系统接口隔开数据库实体类
 *
 * @author linda
 */
public interface Mission {

    interface Factory {

        /**
         * 生成任务接口类
         *
         * @param missionDb 玩家任务数据库实体
         * @return Mission
         */
        Mission create(MissionDb missionDb);
    }

    /**
     * 是否已经完成
     *
     * @param missionId 任务id
     * @return boolean
     */
    boolean hadCompleted(Integer missionId);

    /**
     * 是否进行中
     *
     * @param missionId 任务id
     * @return boolean
     */
    boolean isProceeding(Integer missionId);

    /**
     * 接任务
     *
     * @param playerMission 玩家任务接口
     */
    void acceptMission(PlayerMission playerMission);

    /**
     * 已完成任务列表
     *
     * @return List<PlayerMission>
     */
    List<PlayerMission> getCompleteList();

    /**
     * 正在进行列表
     *
     * @return List<PlayerMission>
     */
    List<PlayerMission> getProceedingList();

    /**
     * 获取玩家指定id的任务信息
     *
     * @param missionId 任务id
     * @return PlayerMission
     */
    PlayerMission getPlayerMission(Integer missionId);
}
