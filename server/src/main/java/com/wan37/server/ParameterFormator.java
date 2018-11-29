package com.wan37.server;

public interface ParameterFormator {

    Integer getParamAsInt(int index);

    String getParamAsString(int index);

    Long getParamAsLong(int index);

    Double getParamAsDouble(int index);
}
