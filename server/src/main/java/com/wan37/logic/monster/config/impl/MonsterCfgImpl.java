package com.wan37.logic.monster.config.impl;

import com.google.common.collect.ImmutableList;
import com.wan37.config.excel.MonsterCfgExcel;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterInitAttrCfg;
import com.wan37.logic.monster.config.MonsterInitSkillCfg;
import com.wan37.logic.monster.config.MonsterItemCfg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linda
 */
public class MonsterCfgImpl implements MonsterCfg {

    public MonsterCfgImpl(MonsterCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;

        attrs = initAttrs();
        skills = initSkills();
        items = initItems();
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
        return attrs;
    }

    @Override
    public List<MonsterInitSkillCfg> getSkills() {
        return skills;
    }

    @Override
    public List<MonsterItemCfg> getItems() {
        return items;
    }

    private List<MonsterInitAttrCfg> initAttrs() {
        String attrs = cfgExcel.getInitAttr();
        if (attrs == null) {
            return ImmutableList.of();
        }

        return Arrays.stream(attrs.split(","))
                .map(this::createAttr)
                .collect(Collectors.toList());
    }

    private List<MonsterInitSkillCfg> initSkills() {
        String skills = cfgExcel.getSkills();
        if (skills == null) {
            return ImmutableList.of();
        }

        return Arrays.stream(skills.split(","))
                .map(this::createSkill)
                .collect(Collectors.toList());
    }

    private List<MonsterItemCfg> initItems() {
        String items = cfgExcel.getItems();
        if (items == null) {
            return ImmutableList.of();
        }

        return Arrays.stream(cfgExcel.getItems().split(","))
                .map(this::createItem)
                .collect(Collectors.toList());
    }

    @Override
    public int getExp() {
        return cfgExcel.getExp();
    }

    private MonsterInitSkillCfg createSkill(String s) {
        String[] skill = s.split(":");
        Integer id = Integer.parseInt(skill[0]);
        int level = Integer.parseInt(skill[1]);

        return new MonsterInitSkillCfgImpl(id, level);
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

    private List<MonsterInitAttrCfg> attrs;
    private List<MonsterInitSkillCfg> skills;
    private List<MonsterItemCfg> items;

}
