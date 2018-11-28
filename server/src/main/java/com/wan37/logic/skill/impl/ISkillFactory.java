package com.wan37.logic.skill.impl;

import com.wan37.logic.skill.ISkill;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.database.PSkillDb;
import org.springframework.stereotype.Service;

@Service
public class ISkillFactory implements ISkill.Factory {

    @Override
    public ISkill create(SkillCfg skillCfg, int level) {
        return new MonsterSkillImpl(skillCfg, level);
    }

    @Override
    public ISkill create(SkillCfg skillCfg, PSkillDb skillDb) {
        return new PlayerSkillImpl(skillCfg, skillDb);
    }
}
