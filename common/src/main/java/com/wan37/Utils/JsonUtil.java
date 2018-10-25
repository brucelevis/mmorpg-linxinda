package com.wan37.Utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    public static String parseJson(Object object) {
        return JSONObject.toJSONString(object);
    }

    public static Object parseObj(String json) {
        return JSONObject.parse(json);
    }
}
