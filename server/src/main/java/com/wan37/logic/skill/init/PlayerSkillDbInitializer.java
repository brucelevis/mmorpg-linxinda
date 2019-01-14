package com.wan37.logic.skill.init;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.faction.config.FactionInitSkillCfg;
import com.wan37.logic.skill.database.PlayerEachSkillDb;
import com.wan37.logic.skill.database.PlayerSkillDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 初始化玩家技能系统数据
 *
 * @author linda
 */
@Service
public class PlayerSkillDbInitializer {

    @Autowired
    private ConfigLoader configLoader;

    public void init(PlayerSkillDb playerSkillDb, Integer factionId) {
        configLoader.load(FactionCfg.class, factionId)
                .ifPresent(c -> initImpl(c, playerSkillDb));
    }

    private void initImpl(FactionCfg factionCfg, PlayerSkillDb playerSkillDb) {
        playerSkillDb.setSkills(factionCfg.getInitSkills().stream()
                .map(this::createSkillDb)
                .collect(Collectors.toMap(PlayerEachSkillDb::getCfgId, Function.identity())));
    }

    private PlayerEachSkillDb createSkillDb(FactionInitSkillCfg cfg) {
        PlayerEachSkillDb db = new PlayerEachSkillDb();
        db.setCfgId(cfg.getSkillCfgId());
        db.setLevel(cfg.getLevel());
        return db;
    }
}
