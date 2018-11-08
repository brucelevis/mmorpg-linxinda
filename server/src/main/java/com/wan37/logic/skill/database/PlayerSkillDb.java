package com.wan37.logic.skill.database;

import com.wan37.logic.skill.SkillCfg;

import java.util.Map;

public class PlayerSkillDb {

    /**
     * key:
     *
     * @see SkillCfg#getId
     */
    private Map<Integer, PSkillDb> skills;

    public Map<Integer, PSkillDb> getSkills() {
        return skills;
    }

    public void setSkills(Map<Integer, PSkillDb> skills) {
        this.skills = skills;
    }
}
