package com.wan37.logic.equipment.config.impl;

import com.wan37.config.entity.EquipCfgExcel;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipInitAttrCfg;
import com.wan37.logic.equipment.config.EquipQualityCfg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EquipCfgImpl implements EquipCfg {

    public EquipCfgImpl(EquipCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;
    }

    @Override
    public Integer getId() {
        return cfgExcel.getId();
    }

    @Override
    public Integer getPart() {
        return cfgExcel.getPart();
    }

    @Override
    public List<EquipInitAttrCfg> getAttrs() {
        return Arrays.stream(cfgExcel.getAttr().split(","))
                .map(this::createAttr)
                .collect(Collectors.toList());
    }

    @Override
    public int getLevel() {
        return cfgExcel.getLevel();
    }

    @Override
    public List<EquipQualityCfg> getQuality() {
        return Arrays.stream(cfgExcel.getQuality().split(","))
                .map(this::createQuality)
                .collect(Collectors.toList());
    }

    private EquipQualityCfg createQuality(String qualityStr) {
        String[] s = qualityStr.split(":");
        int score = Integer.parseInt(s[0]);
        String name = s[1];

        return new EquipQualityCfgImpl(score, name);
    }

    private EquipInitAttrCfg createAttr(String attrStr) {
        String[] s = attrStr.split(":");
        Integer attrCfgId = Integer.parseInt(s[0]);

        String[] args = s[1].split("\\|");
        double value = Double.parseDouble(args[0]);
        double step = Double.parseDouble(args[1]);
        int maxRandInt = Integer.parseInt(args[2]);

        return new EquipInitAttrCfgImpl(attrCfgId, value, step, maxRandInt);
    }

    private final EquipCfgExcel cfgExcel;
}
