package com.wan37.logic.player.service.login;

import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerLoginExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(PLoginPlayer regPlayer) {
        //Player player = playerGlobalManager.selectPlayerByUid(regPlayer.getPlayerUid());

    }
}
