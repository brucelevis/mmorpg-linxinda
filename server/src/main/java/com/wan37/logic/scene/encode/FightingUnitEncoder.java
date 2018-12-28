package com.wan37.logic.scene.encode;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.summoning.encode.SummoningEncoder;
import com.wan37.logic.summoning.entity.Summoning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FightingUnitEncoder {

    @Autowired
    private MonsterEncoder monsterEncoder;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    @Autowired
    private SummoningEncoder summoningEncoder;

    public String encode(FightingUnit unit) {
        if (unit instanceof Monster) {
            Monster monster = (Monster) unit;
            return monsterEncoder.encode(monster);
        } else if (unit instanceof Player) {
            Player player = (Player) unit;
            return playerInfoEncoder.encode(player);
        } else if (unit instanceof Summoning) {
            Summoning summoning = (Summoning) unit;
            return summoningEncoder.encode(summoning);
        }

        return null;
    }
}
