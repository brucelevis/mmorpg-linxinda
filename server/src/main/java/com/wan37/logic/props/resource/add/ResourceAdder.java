package com.wan37.logic.props.resource.add;

import com.wan37.logic.player.Player;
import com.wan37.logic.props.resource.ResourceElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceAdder {

    @Autowired
    private ResourceVirtualItemAdder resourceVirtualItemAdder;

    @Autowired
    private ResourceItemAdder resourceItemAdder;

    public void add(ResourceElement element, Player player) {
        //FIXME: 先写死虚物id小于200
        if (element.getCfgId() < 200) {
            resourceVirtualItemAdder.add(element, player);
            return;
        }

        resourceItemAdder.add(element, player.getPlayerDb().getBackpackDb());
    }
}
