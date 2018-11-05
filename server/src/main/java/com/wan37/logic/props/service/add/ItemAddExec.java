package com.wan37.logic.props.service.add;

import com.google.common.collect.ImmutableList;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemAddExec {

    private static final Logger LOG = Logger.getLogger(ItemAddExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private ResourceFacade resourceFacade;

    public void exec(Integer cfgId, long amount, String channelId) {
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            LOG.info("找不到玩家");
            return;
        }

        ResourceCollection res = createResource(ImmutableList.of(createElement(cfgId, amount)));
        resourceFacade.giveResource(res, player);
    }

    private ResourceElement createElement(Integer cfgId, long amount) {
        return new ResourceElementImpl(cfgId, amount);
    }

    private ResourceCollection createResource(List<ResourceElement> elements) {
        return new ResourceCollectionImpl(elements);
    }
}
