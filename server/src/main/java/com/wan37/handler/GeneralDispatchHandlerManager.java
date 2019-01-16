package com.wan37.handler;

import com.wan37.util.ApplicationContextUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * 对应请求分发管理器
 *
 * @author linda
 */
@Service
public class GeneralDispatchHandlerManager implements ApplicationListener {

    private static final Logger LOG = Logger.getLogger(GeneralDispatchHandlerManager.class);

    /**
     * 用于保存接口实现类名及对应的类
     */
    private static Map<String, GeneralHandler> handlerMap;

    public Optional<GeneralHandler> get(String key) {
        GeneralHandler handler = handlerMap.get(key);
        return handler == null ? Optional.empty() : Optional.of(handler);
    }

    private static void init() {
        handlerMap = ApplicationContextUtil.getBeansOfType(GeneralHandler.class);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        LOG.info("初始化HandlerMap..");
        init();
        LOG.info("初始化HandlerMap完成");
    }
}
