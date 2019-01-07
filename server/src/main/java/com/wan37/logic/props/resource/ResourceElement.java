package com.wan37.logic.props.resource;

/**
 * 单个资源接口
 *
 * @author linda
 */
public interface ResourceElement {

    /**
     * 物品（实物，虚物）id
     *
     * @return Integer
     */
    Integer getCfgId();

    /**
     * 数量
     *
     * @return long
     */
    long getAmount();
}
