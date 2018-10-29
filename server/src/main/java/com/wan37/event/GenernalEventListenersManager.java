package com.wan37.event;


import com.wan37.util.ApplicationContextUtil;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenernalEventListenersManager {

    private static Map<String, List<GeneralEventListener>> listenersMap = null;

    @SuppressWarnings("unchecked")
    public void fireEvent(Object object) {
        if (listenersMap == null) {
            init();
        }

        String eventType = object.getClass().getTypeName();
        getListeners(eventType).forEach(l -> l.execute(object));
    }

    private void init() {
        listenersMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(GeneralEventListener.class)
                .values().stream()
                .collect(Collectors.groupingBy(this::getEventType));

    }

    private String getEventType(Object object) {
        return ((ParameterizedType) object.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
    }

    private List<GeneralEventListener> getListeners(String eventType) {
        return listenersMap.get(eventType);
    }
}