package com.wan37.logic.skill.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.player.Player;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.database.PlayerEachSkillDb;
import com.wan37.logic.skill.database.PlayerSkillDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class SkillInfoExec {

    @Autowired
    private ConfigLoader configLoader;

    public void exec(Player player) {
        PlayerSkillDb playerSkillDb = player.getPlayerDb().getPlayerSkillDb();
        String msg = "技能列表：\n" + encode(playerSkillDb);
        player.syncClient(msg);
    }

    private String encode(PlayerSkillDb playerSkillDb) {
        return playerSkillDb.getSkills().values().stream()
                .map(this::encodeSkill)
                .collect(Collectors.joining("\n"));
    }

    public String encodeSkill(PlayerEachSkillDb playerEachSkillDb) {
        Integer id = playerEachSkillDb.getCfgId();
        return String.format("%s（id：%s）：Lv%s", configLoader.loadName(SkillCfg.class, id), id, playerEachSkillDb.getLevel());
    }
}
