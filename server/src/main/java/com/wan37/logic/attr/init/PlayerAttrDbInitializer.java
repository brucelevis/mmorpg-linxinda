package com.wan37.logic.attr.init;

import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.faction.config.FactionCfgLoader;
import com.wan37.logic.faction.config.FactionInitAttrCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 初始化人物属性数据库实体类
 */
@Service
public class PlayerAttrDbInitializer {

    @Autowired
    private FactionCfgLoader factionCfgLoader;

    public void init(PlayerAttrDb attrDb, Integer factionId) {
        factionCfgLoader.load(factionId)
                .ifPresent(c -> initImpl(attrDb, c));
    }

    private void initImpl(PlayerAttrDb attrDb, FactionCfg factionCfg) {
        attrDb.setAttrs(factionCfg.getInitAttrs().stream()
                .map(this::createAttr)
                .collect(Collectors.toMap(PAttrDb::getCfgId, Function.identity())));
    }

    private PAttrDb createAttr(FactionInitAttrCfg attrCfg) {
        PAttrDb db = new PAttrDb();
        db.setCfgId(attrCfg.getAttrCfgId());
        db.setValue(attrCfg.getValue());
        return db;
    }
}
