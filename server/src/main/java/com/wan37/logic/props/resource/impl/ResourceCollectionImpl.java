package com.wan37.logic.props.resource.impl;

import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.ResourceElement;

import java.util.List;

public class ResourceCollectionImpl implements ResourceCollection {

    public ResourceCollectionImpl(List<ResourceElement> elements) {
        this.elements = elements;
    }

    @Override
    public List<ResourceElement> getElements() {
        return elements;
    }

    private final List<ResourceElement> elements;
}
