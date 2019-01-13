package com.wan37.logic.attr.init;

import com.wan37.logic.attr.database.PlayerEachAttrDb;
import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.faction.config.FactionInitAttrCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 初始化人物属性数据库实体类
 *
 * @author linda
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
                .collect(Collectors.toMap(PlayerEachAttrDb::getCfgId, Function.identity())));
    }

    private PlayerEachAttrDb createAttr(FactionInitAttrCfg attrCfg) {
        PlayerEachAttrDb db = new PlayerEachAttrDb();
        db.setCfgId(attrCfg.getAttrCfgId());
        db.setValue(attrCfg.getValue());
        return db;
    }
}
