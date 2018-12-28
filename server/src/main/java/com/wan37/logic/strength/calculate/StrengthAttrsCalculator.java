package com.wan37.logic.strength.calculate;

import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StrengthAttrsCalculator {

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    public Map<Integer, Double> calc(PlayerDb playerDb) {
        Map<Integer, Double> result = new HashMap<>();

        // 人物基础属性
        PlayerAttrDb playerAttrDb = playerDb.getPlayerAttrDb();
        playerAttrDb.getAttrs().values()
                .forEach(a -> result.merge(a.getCfgId(), a.getValue(), (v1, v2) -> v1 + v2));

        // 装备栏属性
        EquipDb equipDb = playerDb.getEquipDb();
        equipDb.getItems().values().stream()
                .map(ItemDb::getExtraDb)
                .map(e -> equipExtraDbGetter.get(e))
                .map(EquipExtraDb::getBaseAttrs)
                .flatMap(Collection::stream)
                .forEach(a -> result.merge(a.getCfgId(), a.getValue(), (v1, v2) -> v1 + v2));

        return result;
    }
}
