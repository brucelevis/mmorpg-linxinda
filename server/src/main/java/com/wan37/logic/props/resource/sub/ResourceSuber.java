package com.wan37.logic.props.resource.sub;

import com.wan37.logic.player.Player;
import com.wan37.logic.props.resource.ResourceElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消耗资源
 *
 * @author linda
 */
@Service
public class ResourceSuber {

    @Autowired
    private ResourceVirtualItemSuber resourceVirtualItemSuber;

    @Autowired
    private ResourceItemSuber resourceItemSuber;

    public boolean sub(ResourceElement element, Player player) {
        if (element.getCfgId() < 200) {
            return resourceVirtualItemSuber.sub(element, player);
        }

        return resourceItemSuber.sub(element, player);
    }
}
