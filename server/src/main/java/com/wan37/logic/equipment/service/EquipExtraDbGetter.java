package com.wan37.logic.equipment.service;

import com.alibaba.fastjson.JSONObject;
import com.wan37.logic.equipment.database.EquipExtraDb;
import org.springframework.stereotype.Service;

/**
 * 获取装备额外信息实体类
 *
 * @author linda
 */
@Service
public class EquipExtraDbGetter {

    public EquipExtraDb get(Object extraDb) {
        try {
            return (EquipExtraDb) extraDb;
        } catch (Exception e) {
            return JSONObject.parseObject(extraDb.toString(), EquipExtraDb.class);
        }
    }
}
