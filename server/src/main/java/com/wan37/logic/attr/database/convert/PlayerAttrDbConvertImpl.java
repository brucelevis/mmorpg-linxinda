package com.wan37.logic.attr.database.convert;

import com.wan37.Utils.JsonUtil;
import com.wan37.logic.attr.database.PlayerAttrDb;

import javax.persistence.AttributeConverter;

public class PlayerAttrDbConvertImpl implements AttributeConverter<PlayerAttrDb, String> {

    @Override
    public String convertToDatabaseColumn(PlayerAttrDb playerAttrDb) {
        return JsonUtil.parseJson(playerAttrDb);
    }

    @Override
    public PlayerAttrDb convertToEntityAttribute(String s) {
        return (PlayerAttrDb) JsonUtil.parseObj(s, PlayerAttrDb.class);
    }
}
