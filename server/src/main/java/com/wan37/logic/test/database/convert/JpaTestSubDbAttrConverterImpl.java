package com.wan37.logic.test.database.convert;

import com.wan37.util.JsonUtil;
import com.wan37.logic.test.database.JpaTestSubDb;

import javax.persistence.AttributeConverter;

/**
 * @author linda
 */
public class JpaTestSubDbAttrConverterImpl implements AttributeConverter<JpaTestSubDb, String> {

    @Override
    public String convertToDatabaseColumn(JpaTestSubDb jpaTestSubDb) {
        return JsonUtil.parseJson(jpaTestSubDb);
    }

    @Override
    public JpaTestSubDb convertToEntityAttribute(String s) {
        return (JpaTestSubDb) JsonUtil.parseObj(s, JpaTestSubDb.class);
    }
}
