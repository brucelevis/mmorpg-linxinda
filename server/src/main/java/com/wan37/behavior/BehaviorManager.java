package com.wan37.behavior;

import com.google.common.collect.ImmutableMap;
import com.wan37.util.ApplicationContextUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BehaviorManager {

    /**
     * @see Behavior
     */
    private static Map<Class, Map<Integer, Behavior>> bahevMap = null;

    public Behavior get(Class clazz, Integer id) {
        if (bahevMap == null) {
            bahevMap = init();
        }

        return bahevMap.get(clazz).get(id);
    }

    private Map<Class, Map<Integer, Behavior>> init() {
        return ApplicationContextUtil.getApplicationContext().getBeansOfType(Behavior.class)
                .values().stream()
                .collect(Collectors.groupingBy(this::getInterface))
                .entrySet().stream()
                .map(this::toSubBehavMap)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Class getInterface(Behavior behavior) {
        return behavior.getClass().getInterfaces()[0];
    }

    private Map<Class, Map<Integer, Behavior>> toSubBehavMap(Map.Entry<Class, List<Behavior>> entry) {
        return ImmutableMap.of(entry.getKey(), entry.getValue().stream()
                .collect(Collectors.toMap(this::getLogicId, Function.identity())));
    }

    private Integer getLogicId(Behavior behavior) {
        String className = behavior.getClass().getName();
        String logicId = className.replaceAll(".*[^\\d](?=(\\d+))", "");
        return Integer.parseInt(logicId);
    }
}
