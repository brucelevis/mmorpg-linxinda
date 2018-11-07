package com.wan37.logic.props.behavior.init;

import com.wan37.logic.props.config.PropsCfg;

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
