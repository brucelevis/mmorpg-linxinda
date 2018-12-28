package com.wan37.logic.scene.base;

import java.util.List;

public interface IScene extends Runnable {

    Integer getId();

    Long getUid();

    String getName();

    boolean canAttack();

    Integer getType();

    void notify(String msg);

    FightingUnit getTargetUnit(Long uid);

    List<FightingUnit> getAllUnit();
}
