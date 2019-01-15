package com.wan37.logic.test.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.test.behavior.TestBehaviorContext;
import com.wan37.logic.test.behavior.TestBehavior;
import org.springframework.stereotype.Service;

@Service
@BehaviorLogic(id = 2)
class TestBehavior2 implements TestBehavior {

    @Override
    public void behave(TestBehaviorContext context) {
        // NOOP
    }
}
