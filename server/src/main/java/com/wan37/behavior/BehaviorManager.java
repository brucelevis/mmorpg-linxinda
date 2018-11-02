package com.wan37.behavior;

import com.wan37.util.ApplicationContextUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BehaviorManager {

    private static Map<String, Map<Integer, Behavior>> bahevMap = null;

    public Behavior get(Class clazz, Integer id) {
        if (bahevMap == null) {
            init();
        }

        return bahevMap.get(clazz.getName()).get(id);
    }

    private void init() {
        Map<String, Behavior> map = ApplicationContextUtil.getApplicationContext().getBeansOfType(Behavior.class);
    }

    private Pair toPair(Behavior behavior) {
        String className = behavior.getClass().getName();
        String logicId = className.replaceAll(".*[^\\d](?=(\\d+))", "");

        Pair pair = new Pair();
        pair.id = Integer.parseInt(logicId);
        pair.behavior = behavior;
        return pair;
    }

    private static class Pair {

        Integer id;

        Behavior behavior;
    }
}
