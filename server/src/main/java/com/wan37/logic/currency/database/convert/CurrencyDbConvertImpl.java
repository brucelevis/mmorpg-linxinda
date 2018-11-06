package com.wan37.logic.currency.database.convert;


import com.wan37.Utils.JsonUtil;
import com.wan37.logic.currency.database.CurrencyDb;

import javax.persistence.AttributeConverter;

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
