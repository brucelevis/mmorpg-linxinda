package com.wan37.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GeneralDipatchHandlerManager implements ApplicationContextAware {

    /**
     * 用于保存接口实现类名及对应的类
     */
    private static Map<String, GeneralHandler> handlerMap = null;

    /**
     * 获取应用上下文并获取相应的接口实现类
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 根据接口类型返回相应的所有bean
        handlerMap = applicationContext.getBeansOfType(GeneralHandler.class);
    }

    public GeneralHandler get(String key) {
        return handlerMap.get(key);
    }
}
