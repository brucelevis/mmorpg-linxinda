package com.wan37.logic.league.config.impl;

import com.google.common.collect.ImmutableSet;
import com.wan37.config.entity.LeaguePositionCfgExcel;
import com.wan37.logic.league.config.LeaguePositionCfg;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LeaguePositionCfgImpl implements LeaguePositionCfg {

    public LeaguePositionCfgImpl(LeaguePositionCfgExcel cfgExcel) {
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
    public Set<Integer> getPermission() {
        String permission = cfgExcel.getPermission();
        if (permission == null) {
            return ImmutableSet.of();
        }

        return Arrays.stream(cfgExcel.getPermission().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    private final LeaguePositionCfgExcel cfgExcel;
}
