package com.wan37.logic.guild.database.convert;

import com.wan37.util.JsonUtil;

import javax.persistence.AttributeConverter;

/**
 * 公会仓库格子持久化成json到数据库
 *
 * @author linda
 */
public class GuildItemDbConverterImpl implements AttributeConverter<Object, String> {

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
