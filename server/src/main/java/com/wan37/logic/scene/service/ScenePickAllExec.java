package com.wan37.logic.scene.service;

import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.SceneItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ScenePickAllExec {

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    public void exec(Player player) {
        AbstractScene scene = sceneActorSceneGetter.get(player);

        // 枚举所有场景物品，判断可以加不，可以加就加，然后移除场景物品，发通知
        Collection<SceneItem> items = scene.getItems().values();
        for (SceneItem sceneItem : items) {
            ResourceElement res = createElement(sceneItem);
            boolean success = resourceFacade.giveResource(res, player);

            if (!success) {
                continue;
            }

            scene.getItems().remove(sceneItem.getUid());

            String msg = String.format("%s拾取了%s个%s", player.getName(), sceneItem.getAmount(), sceneItem.getName());
            scene.getPlayers().forEach(p -> p.syncClient(msg));
        }

        // 背包更新推送
        backpackUpdateNotifier.notify(player);

        // 虚拟物品更新推送
        currencyUpdateNotifier.notify(player);
    }

    private ResourceElement createElement(SceneItem item) {
        return new ResourceElementImpl(item.getCfgId(), item.getAmount());
    }
}