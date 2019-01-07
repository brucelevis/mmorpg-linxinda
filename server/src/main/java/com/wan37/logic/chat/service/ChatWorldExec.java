package com.wan37.logic.chat.service;

import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.chat.ChatFacade;
import com.wan37.logic.chat.ChatTypeEnum;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class ChatWorldExec {

    @Autowired
    private ChatFacade chatFacade;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    public void exec(Player player, String msg) {
        //FIXME: 写死消耗红色喇叭1个
        ResourceElement cost = new ResourceElementImpl(40001, 1);

        if (!resourceFacade.consumeResource(cost, player)) {
            player.syncClient("当前背包红色喇叭不足1个，世界聊天需要消耗红色喇叭");
            return;
        }

        backpackUpdateNotifier.notify(player);

        String content = String.format("【%s】 [%s]：%s", ChatTypeEnum.CHAT_TYPE_WORLD.getName(), player.getName(), msg);
        chatFacade.chatToWorld(content);
    }
}
