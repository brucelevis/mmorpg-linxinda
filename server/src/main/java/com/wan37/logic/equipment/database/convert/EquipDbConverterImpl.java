package com.wan37.logic.equipment.database.convert;

import com.wan37.util.JsonUtil;
import com.wan37.logic.equipment.database.EquipDb;

import javax.persistence.AttributeConverter;

/**
 * 玩家装备栏信息持久化成Json数据到数据库
 *
 * @author linda
 */
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
