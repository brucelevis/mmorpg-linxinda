package com.wan37.logic.skill.impl;

import com.wan37.logic.skill.Skill;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.database.PlayerEachSkillDb;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class SkillFactory implements Skill.Factory {

    @Override
    public Skill create(SkillCfg skillCfg, int level) {
        return new MonsterSkillImpl(skillCfg, level);
    }

    @Override
    public Skill create(SkillCfg skillCfg, PlayerEachSkillDb skillDb) {
        return new PlayerSkillImpl(skillCfg, skillDb);
    }
}
