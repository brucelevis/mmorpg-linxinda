package com.wan37.logic.props.resource;

import java.util.List;

/**
 * 一组资源
 *
 * @author linda
 */
public interface ResourceCollection {

    /**
     * 返回一组资源
     *
     * @return List<ResourceElement>
     */
    List<ResourceElement> getElements();
}
