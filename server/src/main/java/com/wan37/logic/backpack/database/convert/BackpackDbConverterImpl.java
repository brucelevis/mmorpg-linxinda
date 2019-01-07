package com.wan37.logic.backpack.database.convert;

import com.wan37.util.JsonUtil;
import com.wan37.logic.backpack.database.BackpackDb;

import javax.persistence.AttributeConverter;

/**
 * 玩家背包持久化成json数据到数据库
 *
 * @author linda
 */
public class BackpackDbConverterImpl implements AttributeConverter<BackpackDb, String> {

    @Override
    public String convertToDatabaseColumn(BackpackDb backpackDb) {
        return JsonUtil.parseJson(backpackDb);
    }

    @Override
    public BackpackDb convertToEntityAttribute(String s) {
        return (BackpackDb) JsonUtil.parseObj(s, BackpackDb.class);
    }
}
