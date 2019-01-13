package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.buff.config.BuffCfg;
import com.wan37.logic.buff.config.impl.BuffCfgImpl;

/**
 * Buff配置表实体类
 *
 * @author linda
 */
public class BuffCfgExcel implements ConfigFactory<BuffCfg> {

    private Integer id;
    private String name;
    private Integer effectId;
    private String desc;
    private boolean once;
    private String arg;
    private int interval;
    private int continuous;
    private Integer target;

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

    public Integer getEffectId() {
        return effectId;
    }

    public void setEffectId(Integer effectId) {
        this.effectId = effectId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getContinuous() {
        return continuous;
    }

    public void setContinuous(int continuous) {
        this.continuous = continuous;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    @Override
    public BuffCfg create() {
        return new BuffCfgImpl(this);
    }
}
