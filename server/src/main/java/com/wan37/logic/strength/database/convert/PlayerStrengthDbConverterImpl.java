package com.wan37.logic.strength.database.convert;

import com.wan37.Utils.JsonUtil;
import com.wan37.logic.strength.database.PlayerStrengthDb;

import javax.persistence.AttributeConverter;

public class PlayerStrengthDbConverterImpl implements AttributeConverter<PlayerStrengthDb, String> {

    @Override
    public String convertToDatabaseColumn(PlayerStrengthDb playerStrengthDb) {
        return JsonUtil.parseJson(playerStrengthDb);
    }

    @Override
    public PlayerStrengthDb convertToEntityAttribute(String s) {
        return (PlayerStrengthDb) JsonUtil.parseObj(s, PlayerStrengthDb.class);
    }
}
