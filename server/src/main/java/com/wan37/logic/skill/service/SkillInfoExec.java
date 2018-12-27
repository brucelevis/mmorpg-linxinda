package com.wan37.logic.skill.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.skill.config.SkillCfgLoader;
import com.wan37.logic.skill.database.PSkillDb;
import com.wan37.logic.skill.database.PlayerSkillDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SkillInfoExec {

    @Autowired
    private SkillCfgLoader skillCfgLoader;

    public void exec(Player player) {
        PlayerSkillDb playerSkillDb = player.getPlayerDb().getPlayerSkillDb();
        String msg = "技能列表：\n" + encode(playerSkillDb);
        player.syncClient(msg);
    }

    @Deprecated
    private String encode(PlayerSkillDb playerSkillDb) {
        return playerSkillDb.getSkills().values().stream()
                .map(this::encodeSkill)
                .collect(Collectors.joining("\n"));
    }

    public String encodeSkill(PSkillDb pSkillDb) {
        Integer id = pSkillDb.getCfgId();
        return String.format("%s（id：%s）：Lv%s", skillCfgLoader.getName(id), id, pSkillDb.getLevel());
    }
}
