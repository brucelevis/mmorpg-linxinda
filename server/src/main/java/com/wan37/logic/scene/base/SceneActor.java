package com.wan37.logic.scene.base;

public interface SceneActor {

    Long getUid();

    Integer getSceneId();

    void setSceneId(Integer sceneId);

    String getName();

    long getHp();

    void setHp(long hp);

    long getMaxHp();

    long getMp();

    void setMp(long mp);

    long getMaxMp();

    long getDeadTime();

    void setDeadTime(long time);

    boolean isAlive();

    void setAlive(boolean alive);
}
