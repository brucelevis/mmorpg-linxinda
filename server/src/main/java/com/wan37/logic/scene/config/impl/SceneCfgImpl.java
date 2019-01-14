package com.wan37.logic.scene.config.impl;

import com.google.common.collect.ImmutableSet;
import com.wan37.config.excel.SceneCfgExcel;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneMonsterCfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author linda
 */
public class SceneCfgImpl implements SceneCfg {

    public SceneCfgImpl(SceneCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;

        npcs = initNpcs();
        monsters = initMonsters();
        neighbors = initNeighbors();
    }

    @Override
    public Integer getId() {
        return cfgExcel.getId();
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
        return neighbors;
    }

    @Override
    public List<Integer> getNpcs() {
        return npcs;
    }

    @Override
    public List<SceneMonsterCfg> getMonsters() {
        return monsters;
    }

    @Override
    public boolean canAttack() {
        return cfgExcel.isCanAttack();
    }

    @Override
    public Integer getType() {
        return cfgExcel.getType();
    }

    private List<Integer> initNpcs() {
        String npcs = cfgExcel.getNpcs();
        if (npcs == null) {
            return new ArrayList<>();
        }

        return Arrays.stream(npcs.split("\\|"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<SceneMonsterCfg> initMonsters() {
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

    private Set<Integer> initNeighbors() {
        String neighborStr = cfgExcel.getNeighbor();
        if (neighborStr == null) {
            return ImmutableSet.of();
        }

        return Arrays.stream(neighborStr.split("\\|"))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    private static class Monster {

        Integer cfgId;

        int amount;
    }

    private final SceneCfgExcel cfgExcel;

    private Set<Integer> neighbors;
    private List<SceneMonsterCfg> monsters;
    private List<Integer> npcs;
}
