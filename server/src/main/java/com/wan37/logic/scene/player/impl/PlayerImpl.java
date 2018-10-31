package com.wan37.logic.scene.player.impl;

import com.wan37.Utils.MessageSenderFormatter;
import com.wan37.common.GeneralResponseDto;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.player.ScenePlayer;

class PlayerImpl implements ScenePlayer {

    public PlayerImpl(Player player) {
        this.player = player;
    }

    @Override
    public Long getPlayerUid() {
        return player.getUid();
    }

    @Override
    public String getPlayerName() {
        return player.getName();
    }

    @Override
    public int getLevel() {
        return player.getLevel();
    }

    @Override
    public Integer getFactionId() {
        return player.getFactionId();
    }

    @Override
    public void notify(GeneralResponseDto dto) {
        player.getChannel().writeAndFlush(MessageSenderFormatter.format(dto));
    }

    private final Player player;
}
