package com.wan37.logic.skill.database.convert;

import com.wan37.util.JsonUtil;
import com.wan37.logic.skill.database.PlayerSkillDb;

import javax.persistence.AttributeConverter;

/**
 * 玩家技能系统数据持久化成json到数据库
 *
 * @author linda
 */
public class PlayerSkillDbConverterImpl implements AttributeConverter<PlayerSkillDb, String> {

    @Override
    public String convertToDatabaseColumn(PlayerSkillDb playerSkillDb) {
        return JsonUtil.parseJson(playerSkillDb);
    }

    @Override
    public PlayerSkillDb convertToEntityAttribute(String s) {
        return (PlayerSkillDb) JsonUtil.parseObj(s, PlayerSkillDb.class);
    }
}
