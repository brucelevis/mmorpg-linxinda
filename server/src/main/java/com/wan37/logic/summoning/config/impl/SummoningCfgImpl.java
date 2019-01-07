package com.wan37.logic.summoning.config.impl;

import com.google.common.collect.ImmutableList;
import com.wan37.config.entity.SummoningCfgExcel;
import com.wan37.logic.summoning.config.SummoningCfg;
import com.wan37.logic.summoning.config.SummoningInitAttrCfg;
import com.wan37.logic.summoning.config.SummoningInitSkillCfg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linda
 */
public class SummoningCfgImpl implements SummoningCfg {

    public SummoningCfgImpl(SummoningCfgExcel cfgExcel) {
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
    public List<SummoningInitAttrCfg> getInitAttrs() {
        String attrs = cfgExcel.getInitAttr();
        if (attrs == null) {
            return ImmutableList.of();
        }

        return Arrays.stream(attrs.split(","))
                .map(this::createAttr)
                .collect(Collectors.toList());
    }

    @Override
    public List<SummoningInitSkillCfg> getInitSkills() {
        String skills = cfgExcel.getSkills();
        if (skills == null) {
            return ImmutableList.of();
        }

        return Arrays.stream(skills.split(","))
                .map(this::createSkill)
                .collect(Collectors.toList());
    }

    private SummoningInitSkillCfg createSkill(String s) {
        String[] skill = s.split(":");
        Integer id = Integer.parseInt(skill[0]);
        int level = Integer.parseInt(skill[1]);

        return new SummoningInitSkillCfgImpl(id, level);
    }

    private SummoningInitAttrCfg createAttr(String s) {
        String[] attr = s.split(":");
        Integer id = Integer.parseInt(attr[0]);
        double value = Double.parseDouble(attr[1]);

        return new SummoningInitAttrCfgImpl(id, value);
    }

    private final SummoningCfgExcel cfgExcel;
}
