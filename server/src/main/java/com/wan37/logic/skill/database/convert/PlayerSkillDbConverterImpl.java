package com.wan37.logic.skill.database.convert;

import com.wan37.util.JsonUtil;
import com.wan37.logic.skill.database.PlayerSkillDb;

import javax.persistence.AttributeConverter;

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
