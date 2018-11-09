package com.wan37.logic.equipment.service;

import com.alibaba.fastjson.JSONObject;
import com.wan37.logic.equipment.database.EquipExtraDb;
import org.springframework.stereotype.Service;

@Service
public class EquipExtraDbGetter {

    public EquipExtraDb get(Object extraDb) {
        return JSONObject.parseObject(extraDb.toString(), EquipExtraDb.class);
    }
}
