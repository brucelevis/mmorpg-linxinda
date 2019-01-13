package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.npc.config.NpcCfg;
import com.wan37.logic.npc.config.impl.NpcCfgImpl;

/**
 * Npc配置表实体类
 *
 * @author linda
 */
public class NpcCfgExcel implements ConfigFactory<NpcCfg> {

    private Integer id;
    private String name;
    private String talk;

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

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    @Override
    public NpcCfg create() {
        return new NpcCfgImpl(this);
    }
}
