package com.wan37.logic.backpack.database.convert;

import com.wan37.Utils.JsonUtil;
import com.wan37.logic.backpack.database.BackpackDb;

import javax.persistence.AttributeConverter;

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
