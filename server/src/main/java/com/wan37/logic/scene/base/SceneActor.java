package com.wan37.logic.scene.base;

import com.wan37.logic.scene.config.SceneCfg;

/**
 * 场景生物
 *
 * @author linda
 */
public interface SceneActor {

    /**
     * 唯一id
     *
     * @return Long
     */
    Long getUid();

    /**
     * 所在场景id
     *
     * @return Integer
     * @see SceneCfg#getId()
     */
    Integer getSceneId();

    /**
     * 设置所在场景id
     *
     * @param sceneId 场景id
     */
    void setSceneId(Integer sceneId);

    /**
     * 所在临时场景唯一id
     *
     * @return Long
     */
    Long getSceneUid();

    /**
     * 设置所在临时场景唯一uid
     *
     * @param uid 临时场景唯一id
     */
    void setSceneUid(Long uid);

    /**
     * 名字
     *
     * @return String
     */
    String getName();

    /**
     * 当前血量
     *
     * @return long
     */
    long getHp();

    /**
     * 设置血量
     *
     * @param hp 血量
     */
    void setHp(long hp);

    /**
     * 血量上限
     *
     * @return long
     */
    long getMaxHp();

    /**
     * 当前mp
     *
     * @return long
     */
    long getMp();

    /**
     * 设置mp
     *
     * @param mp 蓝
     */
    void setMp(long mp);

    /**
     * 蓝量上限
     *
     * @return long
     */
    long getMaxMp();

    /**
     * 死亡时间
     *
     * @return long
     */
    long getDeadTime();

    /**
     * 设置死亡时间
     *
     * @param time 死亡时间
     */
    void setDeadTime(long time);

    /**
     * 是否存活
     *
     * @return boolean
     */
    boolean isAlive();

    /**
     * 设置是否存活
     *
     * @param alive 是否存活
     */
    void setAlive(boolean alive);
}
