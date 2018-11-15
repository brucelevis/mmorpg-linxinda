package com.wan37.logic.strength.init;

import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import com.wan37.logic.strength.service.calculate.StrengthAttrsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

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

        Set<Map.Entry<Integer, Double>> entries = result.entrySet();

        //FIXME: 待优化
        db.setBaseAttackVal(Math.round(entries.stream()
                .mapToDouble(this::calcAttack)
                .sum()));

        db.setBaseDefenseVal(Math.round(entries.stream()
                .mapToDouble(this::calcDefense)
                .sum()));

        playerDb.setPlayerStrengthDb(db);
    }

    private double calcAttack(Map.Entry<Integer, Double> entry) {
        double base = attrCfgLoader.load(entry.getKey())
                .map(AttrCfg::getBaseAttackValue)
                .orElse(0.0);

        return base * entry.getValue();
    }

    private double calcDefense(Map.Entry<Integer, Double> entry) {
        double base = attrCfgLoader.load(entry.getKey())
                .map(AttrCfg::getBaseDefenseValue)
                .orElse(0.0);

        return base * entry.getValue();
    }
}
