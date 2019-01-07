package com.wan37.logic.mission.config.impl;

import com.google.common.collect.ImmutableList;
import com.wan37.config.entity.MissionCfgExcel;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionRewardCfg;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author linda
 */
public class MissionCfgImpl implements MissionCfg {

    public MissionCfgImpl(MissionCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;
    }

    @Override
    public Integer getId() {
        return cfgExcel.getId();
    }

    @Override
    public int getLevel() {
        return cfgExcel.getLevel();
    }

    @Override
    public Integer getPreId() {
        return cfgExcel.getPreId();
    }

    @Override
    public Integer getNextId() {
        return cfgExcel.getNextId();
    }

    @Override
    public String getName() {
        return cfgExcel.getName();
    }

    @Override
    public String getDesc() {
        return cfgExcel.getDesc();
    }

    @Override
    public String getDetail() {
        return cfgExcel.getDetail();
    }

    @Override
    public String getCompleteTip() {
        return cfgExcel.getCompleteTip();
    }

    @Override
    public String getProceedTip() {
        return cfgExcel.getProceedTip();
    }

    @Override
    public long getExp() {
        return cfgExcel.getExp();
    }

    @Override
    public List<MissionRewardCfg> getReward() {
        String reward = cfgExcel.getReward();
        if (reward == null) {
            return ImmutableList.of();
        }

        return Arrays.stream(reward.split(","))
                .map(this::createReward)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getNpcId() {
        return cfgExcel.getNpcId();
    }

    @Override
    public Integer getSceneId() {
        return cfgExcel.getSceneId();
    }

    @Override
    public Integer getType() {
        return cfgExcel.getType();
    }

    @Override
    public Integer getTargetId() {
        return cfgExcel.getTargetId();
    }

    @Override
    public int getArgsAsInt() {
        return Integer.parseInt(cfgExcel.getArgs());
    }

    @Override
    public Set<Integer> getArgsAsIntSet() {
        return Arrays.stream(cfgExcel.getArgs().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAutoCommit() {
        return cfgExcel.isAutoCommit();
    }

    @Override
    public boolean isAutoAccept() {
        return cfgExcel.isAutoAccept();
    }

    private MissionRewardCfg createReward(String s) {
        String[] reward = s.split(":");
        Integer id = Integer.parseInt(reward[0]);
        int amount = Integer.parseInt(reward[1]);
        return new MissionRewardCfgImpl(id, amount);
    }

    private final MissionCfgExcel cfgExcel;
}
