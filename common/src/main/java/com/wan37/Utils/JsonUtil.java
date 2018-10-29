package com.wan37.Utils;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

    public static <T> String parseJson(T t) {
        return JSON.toJSONString(t);
    }

    public static <T> Object parseObj(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
