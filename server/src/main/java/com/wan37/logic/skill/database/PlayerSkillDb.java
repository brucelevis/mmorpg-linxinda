package com.wan37.logic.skill.database;

import com.wan37.logic.skill.config.SkillCfg;

import java.util.Map;

/**
 * 玩家技能系统数据库实例
 *
 * @author linda
 */
public class PlayerSkillDb {

    /**
     * key: 技能id
     *
     * @see SkillCfg#getId
     */
    private Map<Integer, PlayerEachSkillDb> skills;

    public Map<Integer, PlayerEachSkillDb> getSkills() {
        return skills;
    }

    public void setSkills(Map<Integer, PlayerEachSkillDb> skills) {
        this.skills = skills;
    }
}
