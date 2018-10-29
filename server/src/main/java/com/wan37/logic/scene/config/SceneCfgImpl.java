package com.wan37.logic.scene.config;

public class SceneCfgImpl implements SceneCfg {

    public SceneCfgImpl(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isPersonal() {
        // FIXME: 写死非私人场景
        return false;
    }

    private final Integer id;
}
