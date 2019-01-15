package com.wan37.behavior;

import com.google.common.collect.ImmutableMap;
import com.wan37.util.ApplicationContextUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class BehaviorManager implements ApplicationListener {

    private static final Logger LOG = Logger.getLogger(BehaviorManager.class);

    private static Map<Class, Map<Integer, Behavior>> behaviorMap;

    public Behavior get(Class clazz, Integer id) {
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
        return behavior.getClass().getAnnotation(BehaviorLogic.class).id();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        LOG.info("初始化BehaviorMap..");
        init();
        LOG.info("初始化BehaviorMap完成");
    }
}
