package com.wan37.logic.mission.entity;

import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.database.PlayerMissionDb;

/**
 * 玩家每个任务信息接口
 *
 * @author linda
 */
public interface PlayerMission {

    interface Factory {

        /**
         * 生成一个玩家任务信息
         *
         * @param playerMissionDb 玩家一个任务信息的数据库实体类
         * @return PlayerMission
         */
        PlayerMission create(PlayerMissionDb playerMissionDb);
    }

    /**
     * 返回玩家单个任务数据库实体类
     *
     * @return PlayerMissionDb
     */
    PlayerMissionDb getPlayerMission();

    /**
     * 完成时间
     *
     * @return long
     */
    long getCompleteTime();

    /**
     * 设置完成时间
     *
     * @param time 完成时间
     */
    void setCompleteTime(long time);

    /**
     * 任务id
     *
     * @return Integer
     * @see MissionCfg#getId()
     */
    Integer getMissionId();

    /**
     * 任务配置表
     *
     * @return MissionCfg
     */
    MissionCfg getMissionCfg();

    /**
     * 是否可完成
     *
     * @return boolean
     */
    boolean canComplete();

    /**
     * 设置可完成
     *
     * @param canComplete 是否可完成
     */
    void setCanComplete(boolean canComplete);

    /**
     * 获取进度
     *
     * @return int
     */
    int getProgress();

    /**
     * 设置进度
     *
     * @param progress 进度
     */
    void setProgress(int progress);
}
