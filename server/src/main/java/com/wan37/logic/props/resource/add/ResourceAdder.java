package com.wan37.logic.props.resource.add;

import com.wan37.logic.player.Player;
import com.wan37.logic.props.resource.ResourceElement;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceAdder {

    private static final Logger LOG = Logger.getLogger(ResourceAdder.class);

    @Autowired
    private ResourceItemAdder resourceItemAdder;

    public void add(ResourceElement element, Player player) {
        //FIXME: 先写死虚物id小于200
        if (element.getCfgId() < 200) {
            addVirtualItem();
            return;
        }

        resourceItemAdder.add(element, player.getPlayerDb().getBackpackDb());
    }

    private void addVirtualItem() {
        // NOOP
    }
}
