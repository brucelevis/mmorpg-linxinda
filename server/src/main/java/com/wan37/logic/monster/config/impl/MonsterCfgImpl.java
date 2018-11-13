package com.wan37.logic.monster.config.impl;

import com.wan37.config.entity.MonsterCfgExcel;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterInitAttrCfg;
import com.wan37.logic.monster.config.MonsterItemCfg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MonsterCfgImpl implements MonsterCfg {

    public MonsterCfgImpl(MonsterCfgExcel cfgExcel) {
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
    public int getCreateCd() {
        return cfgExcel.getCreateCd();
    }

    @Override
    public List<MonsterInitAttrCfg> getAttrs() {
        return Arrays.stream(cfgExcel.getInitAttr().split(","))
                .map(this::createAttr)
                .collect(Collectors.toList());
    }

    @Override
    public List<MonsterItemCfg> getItems() {
        return Arrays.stream(cfgExcel.getItems().split(","))
                .map(this::createItem)
                .collect(Collectors.toList());
    }

    @Override
    public int getExp() {
        return cfgExcel.getExp();
    }

    private MonsterInitAttrCfg createAttr(String s) {
        String[] attr = s.split(":");
        Integer id = Integer.parseInt(attr[0]);
        double value = Double.parseDouble(attr[1]);

        return new MonsterInitAttrCfgImpl(id, value);
    }

    private MonsterItemCfg createItem(String s) {
        String[] item = s.split(":");
        Integer id = Integer.parseInt(item[0]);
        int amount = Integer.parseInt(item[1]);
        double pro = Double.parseDouble(item[2]);

        return new MonsterItemCfgImpl(id, amount, pro);
    }

    private final MonsterCfgExcel cfgExcel;
}
