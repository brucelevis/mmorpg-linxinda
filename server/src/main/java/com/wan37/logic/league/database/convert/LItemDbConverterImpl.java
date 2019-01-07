package com.wan37.logic.league.database.convert;

import com.wan37.util.JsonUtil;

import javax.persistence.AttributeConverter;

public class LItemDbConverterImpl implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object o) {
        if (o == null) {
            return null;
        }

        return JsonUtil.parseJson(o);
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return JsonUtil.parseObj(s, Object.class);
    }
}
