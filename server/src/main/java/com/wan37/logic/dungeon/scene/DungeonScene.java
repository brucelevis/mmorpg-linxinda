package com.wan37.logic.dungeon.scene;

import com.wan37.logic.scene.base.IScene;
import com.wan37.logic.scene.base.AbstractScene;

/**
 * 副本场景
 */
public class DungeonScene extends AbstractScene implements IScene {

    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public void run() {
        // NOOP
    }
}
