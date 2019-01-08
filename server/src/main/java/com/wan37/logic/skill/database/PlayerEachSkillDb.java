package com.wan37.logic.skill.database;

import com.wan37.logic.skill.config.SkillCfg;

/**
 * 玩家一个技能数据信息
 *
 * @author linda
 */
public class PlayerEachSkillDb {

    /**
     * @see SkillCfg#getId()
     */
    private Integer cfgId;

    /**
     * 技能等级
     */
    private int level;

    /**
     * 上次施放时间
     */
    private long lastUseTime;

    public Integer getCfgId() {
        return cfgId;
    }

    public void setCfgId(Integer cfgId) {
        this.cfgId = cfgId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(long lastUseTime) {
        this.lastUseTime = lastUseTime;
    }
}
