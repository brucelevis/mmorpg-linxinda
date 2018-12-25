package com.wan37.logic.dungeon.config.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.wan37.config.entity.DungeonCfgExcel;
import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.config.DungeonMonsterCfg;
import com.wan37.logic.dungeon.config.DungeonMonsterGroupCfg;
import com.wan37.logic.dungeon.config.DungeonRewardCfg;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DungeonCfgImpl implements DungeonCfg {

    public DungeonCfgImpl(DungeonCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;
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
    public int getLimitTime() {
        return cfgExcel.getLimitTime();
    }

    @Override
    public Integer getSceneId() {
        return cfgExcel.getSceneId();
    }

    @Override
    public String getDesc() {
        return cfgExcel.getDesc();
    }

    @Override
    public List<DungeonRewardCfg> getReward() {
        String reward = cfgExcel.getReward();
        if (reward == null) {
            return ImmutableList.of();
        }

        return Arrays.stream(reward.split(","))
                .map(this::createReward)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, DungeonMonsterGroupCfg> getMonsters() {
        String monsters = cfgExcel.getMonsters();
        if (monsters == null) {
            return ImmutableMap.of();
        }

        String[] group = monsters.split("\\|");
        return IntStream.range(0, group.length)
                .mapToObj(i -> createMonsterGroup(i + 1, group[i]))
                .collect(Collectors.toMap(DungeonMonsterGroupCfg::getGroupId, Function.identity()));
    }

    @Override
    public String getCompleteTip() {
        return cfgExcel.getCompleteTip();
    }

    @Override
    public int getLimitNum() {
        return cfgExcel.getLimitNum();
    }

    private DungeonRewardCfg createReward(String s) {
        String[] reward = s.split(":");

        Integer id = Integer.parseInt(reward[0]);
        int amount = Integer.parseInt(reward[1]);
        double pro = Double.parseDouble(reward[2]);

        return new RewardCfgImpl(id, amount, pro);
    }

    private DungeonMonsterGroupCfg createMonsterGroup(Integer groupId, String s) {
        List<DungeonMonsterCfg> monsters = Arrays.stream(s.split(","))
                .map(this::createMonster)
                .collect(Collectors.toList());

        return new MonsterGroupCfgImpl(groupId, monsters);
    }

    private DungeonMonsterCfg createMonster(String s) {
        String[] monster = s.split(":");

        Integer id = Integer.parseInt(monster[0]);
        int amount = Integer.parseInt(monster[1]);

        return new MonsterCfgImpl(id, amount);
    }

    private final DungeonCfgExcel cfgExcel;
}
