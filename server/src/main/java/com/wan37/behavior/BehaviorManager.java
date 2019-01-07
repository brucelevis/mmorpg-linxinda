package com.wan37.behavior;

import com.google.common.collect.ImmutableMap;
import com.wan37.util.ApplicationContextUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class BehaviorManager {

    private static Map<Class, Map<Integer, Behavior>> behaviorMap;

    /**
     * 切割最后一串数字的正则表达式 如：Behavior1 -> 1
     */
    private static final String THE_TAIL_NUMBER_REGEX = ".*[^\\d](?=(\\d+))";

    public Behavior get(Class clazz, Integer id) {
        if (behaviorMap == null) {
            init();
        }

        return behaviorMap.get(clazz).get(id);
    }

    private void init() {
        behaviorMap = getBeanOfBehaviorType().values().stream()
                .collect(Collectors.groupingBy(this::getInterface))
                .entrySet().stream()
                .map(this::toSubBehaviorMap)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Behavior> getBeanOfBehaviorType() {
        return ApplicationContextUtil.getBeansOfType(Behavior.class);
    }

    private Class getInterface(Behavior behavior) {
        return behavior.getClass().getInterfaces()[0];
    }

    private Map<Class, Map<Integer, Behavior>> toSubBehaviorMap(Map.Entry<Class, List<Behavior>> entry) {
        Class key = entry.getKey();
        Map<Integer, Behavior> value = entry.getValue().stream()
                .collect(Collectors.toMap(this::getLogicId, Function.identity()));

        return ImmutableMap.of(key, value);
    }

    private Integer getLogicId(Behavior behavior) {
        String className = behavior.getClass().getName();
        String logicId = className.replaceAll(THE_TAIL_NUMBER_REGEX, "");
        return Integer.parseInt(logicId);
    }
}
