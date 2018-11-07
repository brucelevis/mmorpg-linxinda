package com.wan37.logic.equipment.database.convert;

import com.wan37.Utils.JsonUtil;
import com.wan37.logic.equipment.database.EquipDb;

import javax.persistence.AttributeConverter;

public class EquipDbConverterImpl implements AttributeConverter<EquipDb, String> {

    @Override
    public String convertToDatabaseColumn(EquipDb equipDb) {
        return JsonUtil.parseJson(equipDb);
    }

    @Override
    public EquipDb convertToEntityAttribute(String s) {
        return (EquipDb) JsonUtil.parseObj(s, EquipDb.class);
    }
}
