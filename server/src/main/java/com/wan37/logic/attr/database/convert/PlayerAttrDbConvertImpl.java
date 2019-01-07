package com.wan37.logic.attr.database.convert;

import com.wan37.util.JsonUtil;
import com.wan37.logic.attr.database.PlayerAttrDb;

import javax.persistence.AttributeConverter;

/**
 * 玩家基础属性持久化成json数据到数据库
 *
 * @author linda
 */
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
