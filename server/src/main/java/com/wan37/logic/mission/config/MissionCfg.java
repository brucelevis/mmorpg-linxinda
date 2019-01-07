package com.wan37.logic.mission.config;

import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.npc.config.NpcCfg;
import com.wan37.logic.scene.config.SceneCfg;

import java.util.List;
import java.util.Set;

/**
 * 任务配置表
 *
 * @author linda
 */
public interface MissionCfg {

    /**
     * 唯一id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 等级要求
     *
     * @return int
     */
    int getLevel();

    /**
     * 前置任务id
     *
     * @return Integer
     * @see MissionCfg#getId()
     */
    Integer getPreId();

    /**
     * 下个任务id
     *
     * @return Integer
     * @see MissionCfg#getId()
     */
    Integer getNextId();

    /**
     * 任务名
     *
     * @return String
     */
    String getName();

    /**
     * 描述
     *
     * @return String
     */
    String getDesc();

    /**
     * 任务详情
     *
     * @return String
     */
    String getDetail();

    /**
     * 完成tip
     *
     * @return String
     */
    String getCompleteTip();

    /**
     * 进行时tip
     *
     * @return String
     */
    String getProceedTip();

    /**
     * 经验奖励
     *
     * @return long
     */
    long getExp();

    /**
     * 任务奖励配置
     *
     * @return List<MissionRewardCfg>
     */
    List<MissionRewardCfg> getReward();

    /**
     * npcId
     *
     * @return Integer
     * @see NpcCfg#getId()
     */
    Integer getNpcId();

    /**
     * 场景id
     *
     * @return Integer
     * @see SceneCfg#getId()
     */
    Integer getSceneId();

    /**
     * 任务类型
     *
     * @return Integer
     * @see MissionTypeEnum#getId()
     */
    Integer getType();

    /**
     * 目标id
     *
     * @return Integer
     */
    Integer getTargetId();

    /**
     * 返回整型参数
     *
     * @return int
     */
    int getArgsAsInt();

    /**
     * 返回整型参数集合
     *
     * @return Set<Integer>
     */
    Set<Integer> getArgsAsIntSet();

    /**
     * 是否自动提交
     *
     * @return boolean
     */
    boolean isAutoCommit();

    /**
     * 是否自动接取
     *
     * @return boolean
     */
    boolean isAutoAccept();
}
