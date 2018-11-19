package com.wan37.logic.attr.service;

import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.attr.encode.PlayerAttrInfoEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import com.wan37.logic.strength.encode.PlayerStrengthInfoEncoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttrInfoExec {

    private static final Logger LOG = Logger.getLogger(AttrInfoExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PlayerAttrInfoEncoder playerAttrInfoEncoder;

    @Autowired
    private PlayerStrengthInfoEncoder playerStrengthInfoEncoder;

    public void exec(Player player) {
        PlayerDb playerDb = player.getPlayerDb();

        PlayerAttrDb playerAttrDb = playerDb.getPlayerAttrDb();
        String pBaseAttr = playerAttrInfoEncoder.encode(playerAttrDb);
        player.syncClient(pBaseAttr);

        PlayerStrengthDb playerStrengthDb = playerDb.getPlayerStrengthDb();
        String strength = playerStrengthInfoEncoder.encode(playerStrengthDb);
        player.syncClient(strength);
    }
}
