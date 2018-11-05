package com.wan37.logic.props;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.add.ResourceAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceFacade {

    @Autowired
    private ResourceAdder resourceAdder;

    @Autowired
    private PlayerDao playerDao;

    public void giveResource(ResourceCollection res, Player player) {
        res.getElements().forEach(e -> resourceAdder.add(e, player));
        playerDao.save(player.getPlayerDb());

        //TODO: 背包更新推送
    }
}