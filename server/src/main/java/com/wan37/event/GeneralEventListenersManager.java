package com.wan37.event;


import com.wan37.util.ApplicationContextUtil;
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
public class GeneralEventListenersManager {

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
        if (listenerMap == null) {
            init();
        }
        return listenerMap.get(eventType);
    }

    private String getEventType(Object object) {
        return ((ParameterizedType) object.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
    }
}