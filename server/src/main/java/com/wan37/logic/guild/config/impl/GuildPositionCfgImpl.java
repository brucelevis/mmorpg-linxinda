package com.wan37.logic.guild.config.impl;

import com.google.common.collect.ImmutableSet;
import com.wan37.config.excel.GuildPositionCfgExcel;
import com.wan37.logic.guild.config.GuildPositionCfg;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author linda
 */
public class GuildPositionCfgImpl implements GuildPositionCfg {

    public GuildPositionCfgImpl(GuildPositionCfgExcel cfgExcel) {
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

    private final GuildPositionCfgExcel cfgExcel;
}
