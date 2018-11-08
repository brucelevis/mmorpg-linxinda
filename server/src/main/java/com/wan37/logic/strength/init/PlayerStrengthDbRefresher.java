package com.wan37.logic.strength.init;

import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import com.wan37.logic.strength.service.calculate.StrengthAttrsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayerStrengthDbRefresher {

    @Autowired
    private StrengthAttrsCalculator strengthAttrsCalculator;

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    public void refresh(PlayerDb playerDb) {
        Map<Integer, Double> result = strengthAttrsCalculator.calc(playerDb);

        PlayerStrengthDb db = new PlayerStrengthDb();
        db.setAttrs(result);
        db.setBaseVal(result.entrySet().stream()
                .mapToDouble(this::calc)
                .sum());

        playerDb.setPlayerStrengthDb(db);
    }

    private double calc(Map.Entry<Integer, Double> entry) {
        double base = attrCfgLoader.load(entry.getKey())
                .map(AttrCfg::getBaseValue)
                .orElse(0.0);

        return base * entry.getValue();
    }
}
