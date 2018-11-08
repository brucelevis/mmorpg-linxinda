package com.wan37.common.notify;

import com.wan37.common.resp.RespPlayerStrengthAttrDto;

import java.util.List;

public class PlayerStrengthUpdateNotify {

    private List<RespPlayerStrengthAttrDto> attrs;
    private double baseValue;

    public List<RespPlayerStrengthAttrDto> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<RespPlayerStrengthAttrDto> attrs) {
        this.attrs = attrs;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }
}
