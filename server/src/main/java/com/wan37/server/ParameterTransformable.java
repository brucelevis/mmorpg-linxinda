package com.wan37.server;

/**
 * 请求参数转化接口
 *
 * @author linda
 */
public interface ParameterTransformable {

    /**
     * 将参数转化成Integer
     *
     * @param index 参数位置下标
     * @return Integer
     */
    Integer getParamAsInt(int index);


    /**
     * 将参数转化成String
     *
     * @param index 参数位置下标
     * @return String
     */
    String getParamAsString(int index);

    /**
     * 将参数转化成Long
     *
     * @param index 参数位置下标
     * @return Long
     */
    Long getParamAsLong(int index);
}
