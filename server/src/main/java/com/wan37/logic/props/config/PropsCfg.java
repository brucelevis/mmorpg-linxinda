package com.wan37.logic.props.config;

/**
 * 实物配置表
 *
 * @author linda
 */
public interface PropsCfg {

    /**
     * 唯一id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 名字
     *
     * @return String
     */
    String getName();

    /**
     * 描述
     *
     * @return String
     */
    String getDesc();

    /**
     * 最大堆叠
     *
     * @return int
     */
    int getMaxOverLay();

    /**
     * 是否可使用
     *
     * @return boolean
     */
    boolean isCanUse();

    /**
     * 使用逻辑id
     *
     * @return Integer
     */
    Integer getUseLogicId();

    /**
     * 使用逻辑参数
     *
     * @return String
     */
    String getUseLogicArgs();

    /**
     * 类型id
     *
     * @return Integer
     */
    Integer getType();
}
