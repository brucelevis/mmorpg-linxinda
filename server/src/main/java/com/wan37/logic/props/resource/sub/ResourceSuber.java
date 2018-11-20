package com.wan37.logic.props.resource.sub;

import com.wan37.logic.player.Player;
import com.wan37.logic.props.resource.ResourceElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceSuber {

    @Autowired
    private ResourceVirtualItemSuber resourceVirtualItemSuber;

    public boolean sub(ResourceElement element, Player player) {
        if (element.getCfgId() < 200) {
            return resourceVirtualItemSuber.sub(element, player);
        }

        //TODO: 扣实物
        return false;
    }
}
