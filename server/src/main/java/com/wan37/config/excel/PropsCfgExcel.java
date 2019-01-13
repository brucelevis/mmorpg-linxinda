package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.impl.PropsCfgImpl;

/**
 * 实物（可进背包）配置表实体类
 *
 * @author linda
 */
public class PropsCfgExcel implements ConfigFactory<PropsCfg> {

    private Integer id;
    private String name;
    private String desc;
    private int maxOverlay;
    private boolean canUse;
    private Integer useLogicId;
    private String useLogicArgs;
    private Integer type;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMaxOverlay() {
        return maxOverlay;
    }

    public void setMaxOverlay(int maxOverlay) {
        this.maxOverlay = maxOverlay;
    }

    public boolean isCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }

    public Integer getUseLogicId() {
        return useLogicId;
    }

    public void setUseLogicId(Integer useLogicId) {
        this.useLogicId = useLogicId;
    }

    public String getUseLogicArgs() {
        return useLogicArgs;
    }

    public void setUseLogicArgs(String useLogicArgs) {
        this.useLogicArgs = useLogicArgs;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public PropsCfg create() {
        return new PropsCfgImpl(this);
    }
}
