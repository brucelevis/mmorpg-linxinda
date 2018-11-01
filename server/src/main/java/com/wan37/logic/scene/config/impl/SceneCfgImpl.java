package com.wan37.logic.scene.config.impl;

import com.wan37.config.entity.SceneCfgExcel;
import com.wan37.logic.scene.config.SceneCfg;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SceneCfgImpl implements SceneCfg {

    public SceneCfgImpl(SceneCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;
    }

    @Override
    public Integer getId() {
        return cfgExcel.getId();
    }

    @Override
    public boolean isPersonal() {
        return cfgExcel.isPersonal();
    }

    @Override
    public String getName() {
        return cfgExcel.getName();
    }

    @Override
    public boolean isDefault() {
        return cfgExcel.isDefaultScene();
    }

    @Override
    public Set<Integer> getNeighbor() {
        return Arrays.stream(cfgExcel.getNeighbor().split("\\|"))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    private final SceneCfgExcel cfgExcel;
}
