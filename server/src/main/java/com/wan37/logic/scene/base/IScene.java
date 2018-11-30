package com.wan37.logic.scene.base;

public interface IScene extends Runnable {

    Integer getId();

    Long getUid();

    String getName();

    boolean canAttack();

    Integer getType();
}
