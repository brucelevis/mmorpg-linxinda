package com.wan37.logic.test.database;

import com.wan37.Utils.JsonUtil;

import javax.persistence.AttributeConverter;

public class JpaTestSubDbAttrConverterImpl implements AttributeConverter<JpaTestSubDb, String> {

    @Override
    public String convertToDatabaseColumn(JpaTestSubDb jpaTestSubDb) {
        return JsonUtil.parseJson(jpaTestSubDb);
    }

    @Override
    public JpaTestSubDb convertToEntityAttribute(String s) {
        return (JpaTestSubDb) JsonUtil.parseObj(s);
    }
}
