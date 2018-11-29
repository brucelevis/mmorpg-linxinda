package com.wan37.logic.scene.base;

public interface IScene extends Runnable {

    Integer getId();

    String getName();

    boolean canAttack();

    Integer getType();
}
