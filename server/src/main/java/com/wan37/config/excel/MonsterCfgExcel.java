package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.impl.MonsterCfgImpl;

/**
 * 怪物配置表实体类
 *
 * @author linda
 */
public class MonsterCfgExcel implements ConfigFactory<MonsterCfg> {

    private Integer id;
    private String name;
    private String initAttr;
    private String skills;
    private int createCd;
    private int exp;
    private String items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitAttr() {
        return initAttr;
    }

    public void setInitAttr(String initAttr) {
        this.initAttr = initAttr;
    }

    public int getCreateCd() {
        return createCd;
    }

    public void setCreateCd(int createCd) {
        this.createCd = createCd;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public MonsterCfg create() {
        return new MonsterCfgImpl(this);
    }
}
