package com.wan37.logic.scene.config.impl;

import com.wan37.config.entity.SceneCfgExcel;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneMonsterCfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Override
    public List<SceneMonsterCfg> getMonsters() {
        String monsters = cfgExcel.getMonsters();
        if (monsters == null) {
            return new ArrayList<>();
        }

        return Arrays.stream(monsters.split(","))
                .map(s -> s.split(":"))
                .map(this::toMonster)
                .map(m -> new SceneMonsterCfgImpl(m.cfgId, m.amount))
                .collect(Collectors.toList());
    }

    private Monster toMonster(String[] s) {
        Monster monster = new Monster();
        monster.cfgId = Integer.parseInt(s[0]);
        monster.amount = Integer.parseInt(s[1]);
        return monster;
    }

    private static class Monster {

        Integer cfgId;

        int amount;
    }

    private final SceneCfgExcel cfgExcel;
}
