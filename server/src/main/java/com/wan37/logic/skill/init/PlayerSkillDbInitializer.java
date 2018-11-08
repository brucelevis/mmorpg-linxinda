package com.wan37.logic.skill.init;

import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.faction.config.FactionCfgLoader;
import com.wan37.logic.faction.config.FactionInitSkillCfg;
import com.wan37.logic.skill.database.PSkillDb;
import com.wan37.logic.skill.database.PlayerSkillDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PlayerSkillDbInitializer {

    @Autowired
    private FactionCfgLoader factionCfgLoader;

    public void init(PlayerSkillDb playerSkillDb, Integer factionId) {
        factionCfgLoader.load(factionId)
                .ifPresent(c -> initImpl(c, playerSkillDb));
    }

    private void initImpl(FactionCfg factionCfg, PlayerSkillDb playerSkillDb) {
        playerSkillDb.setSkills(factionCfg.getInitSkills().stream()
                .map(this::createSkillDb)
                .collect(Collectors.toMap(PSkillDb::getCfgId, Function.identity())));
    }

    private PSkillDb createSkillDb(FactionInitSkillCfg cfg) {
        PSkillDb db = new PSkillDb();
        db.setCfgId(cfg.getSkillCfgId());
        db.setLevel(cfg.getLevel());
        return db;
    }
}
