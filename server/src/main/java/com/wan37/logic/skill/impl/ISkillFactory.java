package com.wan37.logic.skill.impl;

import com.wan37.logic.skill.ISkill;
import com.wan37.logic.skill.config.SkillCfg;
import org.springframework.stereotype.Service;

@Service
public class ISkillFactory implements ISkill.Factory {

    @Override
    public ISkill create(SkillCfg skillCfg, int level) {
        return new SkillImpl(skillCfg, level);
    }
}
