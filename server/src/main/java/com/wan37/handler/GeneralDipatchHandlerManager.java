package com.wan37.handler;

import com.wan37.util.ApplicationContextUtil;

import java.util.Map;
import java.util.Optional;

public class GeneralDipatchHandlerManager {

    /**
     * 用于保存接口实现类名及对应的类
     */
    private static Map<String, GeneralHandler> handlerMap = null;

    public static Optional<GeneralHandler> get(String key) {
        if (handlerMap == null) {
            handlerMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(GeneralHandler.class);
        }

        GeneralHandler handler = handlerMap.get(key);
        return handler == null ? Optional.empty() : Optional.of(handler);
    }
}
