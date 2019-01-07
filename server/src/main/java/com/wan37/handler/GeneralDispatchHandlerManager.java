package com.wan37.handler;

import com.wan37.util.ApplicationContextUtil;

import java.util.Map;
import java.util.Optional;

/**
 * 对应请求分发管理器
 *
 * @author linda
 */
public class GeneralDispatchHandlerManager {

    /**
     * 用于保存接口实现类名及对应的类
     */
    private static Map<String, GeneralHandler> handlerMap;

    public static Optional<GeneralHandler> get(String key) {
        if (handlerMap == null) {
            init();
        }

        GeneralHandler handler = handlerMap.get(key);
        return handler == null ? Optional.empty() : Optional.of(handler);
    }

    private static void init() {
        handlerMap = ApplicationContextUtil.getBeansOfType(GeneralHandler.class);
    }
}
