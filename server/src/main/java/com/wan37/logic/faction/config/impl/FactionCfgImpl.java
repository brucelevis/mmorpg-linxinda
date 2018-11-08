package com.wan37.logic.faction.config.impl;

import com.wan37.config.entity.FactionCfgExcel;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.faction.config.FactionInitAttrCfg;
import com.wan37.logic.faction.config.FactionInitSkillCfg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FactionCfgImpl implements FactionCfg {

    public FactionCfgImpl(FactionCfgExcel cfgExcel) {
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
    public List<FactionInitAttrCfg> getInitAttrs() {
        return Arrays.stream(cfgExcel.getInitAttr().split(","))
                .map(this::toAttrCfg)
                .collect(Collectors.toList());
    }

    @Override
    public List<FactionInitSkillCfg> getInitSkills() {
        return Arrays.stream(cfgExcel.getInitSkill().split(","))
                .map(this::toSkillCfg)
                .collect(Collectors.toList());
    }

    private FactionInitAttrCfgImpl toAttrCfg(String attrStr) {
        String[] s = attrStr.split(":");

        Integer attrCfgId = Integer.parseInt(s[0]);
        double value = Double.parseDouble(s[1]);

        return new FactionInitAttrCfgImpl(attrCfgId, value);
    }

    private FactionInitSkillCfgImpl toSkillCfg(String skillStr) {
        String[] s = skillStr.split(":");

        Integer skillCfgId = Integer.parseInt(s[0]);
        int value = Integer.parseInt(s[1]);

        return new FactionInitSkillCfgImpl(skillCfgId, value);
    }

    private final FactionCfgExcel cfgExcel;
}
