package com.wan37.logic.equipment.config.impl;

import com.wan37.config.entity.EquipCfgExcel;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipInitAttrCfg;

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

    private EquipInitAttrCfgImpl createAttr(String attrStr) {
        String[] s = attrStr.split(":");

        Integer attrCfgId = Integer.parseInt(s[0]);
        double value = Double.parseDouble(s[1]);

        return new EquipInitAttrCfgImpl(attrCfgId, value);
    }

    private final EquipCfgExcel cfgExcel;
}
