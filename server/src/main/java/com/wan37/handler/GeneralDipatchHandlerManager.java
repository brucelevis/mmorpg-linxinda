package com.wan37.handler;

import com.wan37.util.ApplicationContextUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GeneralDipatchHandlerManager {

    /**
     * 用于保存接口实现类名及对应的类
     */
    private static Map<String, GeneralHandler> handlerMap = null;

    public GeneralHandler get(String key) {
        if (handlerMap == null) {
            handlerMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(GeneralHandler.class);
        }

        return handlerMap.get(key);
    }
}
