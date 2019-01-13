package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.player.config.ExpCfg;
import com.wan37.logic.player.config.impl.ExpCfgImpl;

/**
 * 玩家经验配置表实体类
 *
 * @author linda
 */
public class ExpCfgExcel implements ConfigFactory<ExpCfg> {

    private Integer id;
    private long exp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    @Override
    public ExpCfg create() {
        return new ExpCfgImpl(this);
    }
}
