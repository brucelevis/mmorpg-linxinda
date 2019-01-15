package com.wan37.event;


import com.wan37.util.ApplicationContextUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 事件监听管理器
 *
 * @author linda
 */
@Service
public class GeneralEventListenersManager implements ApplicationListener {

    private static final Logger LOG = Logger.getLogger(GeneralEventListenersManager.class);

    private static Map<String, List<GeneralEventListener>> listenerMap;

    @SuppressWarnings("unchecked")
    public void fireEvent(Object object) {
        String eventType = object.getClass().getTypeName();
        getListeners(eventType).forEach(l -> l.execute(object));
    }

    private void init() {
        listenerMap = ApplicationContextUtil.getBeansOfType(GeneralEventListener.class)
                .values().stream()
                .collect(Collectors.groupingBy(this::getEventType));
    }

    private List<GeneralEventListener> getListeners(String eventType) {
        return listenerMap.get(eventType);
    }

    private String getEventType(Object object) {
        return ((ParameterizedType) object.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        LOG.info("初始化ListenerMap..");
        init();
        LOG.info("初始化ListenerMap完成");
    }
}