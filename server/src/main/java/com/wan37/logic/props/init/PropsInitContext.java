package com.wan37.logic.props.init;

import com.wan37.logic.props.config.PropsCfg;

/**
 * 物品初始化上下文
 *
 * @author linda
 */
public class PropsInitContext {

    public PropsInitContext(PropsCfg propsCfg) {
        this.propsCfg = propsCfg;
    }

    public PropsCfg getPropsCfg() {
        return propsCfg;
    }

    public Object getExtraDb() {
        return extraDb;
    }

    public void setExtraDb(Object extraDb) {
        this.extraDb = extraDb;
    }

    private final PropsCfg propsCfg;
    private Object extraDb;
}
