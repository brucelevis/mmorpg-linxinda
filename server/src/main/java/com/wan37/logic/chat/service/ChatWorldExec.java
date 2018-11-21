package com.wan37.logic.chat.service;

import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.chat.ChatTypeEnum;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChatWorldExec {

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    public void exec(Player player, String msg) {
        //FIXME: 写死消耗红色喇叭1个
        ResourceElement cost = new ResourceElementImpl(40001, 1);

        if (!resourceFacade.consumeResource(cost, player)) {
            return;
        }

        backpackUpdateNotifier.notify(player);

        String content = String.format("【%s】 [%s]：%s", ChatTypeEnum.CHAT_TYPE_WORLD.getName(), player.getName(), msg);
        sceneGlobalManager.getAllScenes().stream()
                .map(Scene::getPlayers)
                .flatMap(Collection::stream)
                .forEach(p -> p.syncClient(content));
    }
}
