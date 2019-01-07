package com.wan37.logic.currency.database.convert;


import com.wan37.util.JsonUtil;
import com.wan37.logic.currency.database.CurrencyDb;

import javax.persistence.AttributeConverter;

/**
 * 玩家虚物持久化成Json数据到数据库
 */
public class CurrencyDbConvertImpl implements AttributeConverter<CurrencyDb, String> {

    @Override
    public String convertToDatabaseColumn(CurrencyDb currencyDb) {
        return JsonUtil.parseJson(currencyDb);
    }

    @Override
    public CurrencyDb convertToEntityAttribute(String s) {
        return (CurrencyDb) JsonUtil.parseObj(s, CurrencyDb.class);
    }
}
