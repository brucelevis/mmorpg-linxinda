package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.props.config.impl.VirtualItemCfgImpl;

/**
 * 虚物（如钱，积分等）配置表实体类
 *
 * @author linda
 */
public class VirtualItemCfgExcel implements ConfigFactory<VirtualItemCfg> {

    private Integer id;
    private String name;
    private long maxOverlay;

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

    public long getMaxOverlay() {
        return maxOverlay;
    }

    public void setMaxOverlay(long maxOverlay) {
        this.maxOverlay = maxOverlay;
    }

    @Override
    public VirtualItemCfg create() {
        return new VirtualItemCfgImpl(this);
    }
}
