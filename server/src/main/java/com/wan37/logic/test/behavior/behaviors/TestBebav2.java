package com.wan37.logic.test.behavior.behaviors;

import com.wan37.logic.test.behavior.TestBehavContext;
import com.wan37.logic.test.behavior.TestBehavior;
import org.springframework.stereotype.Service;

@Service
class TestBebav2 implements TestBehavior<TestBehavContext> {

    @Override
    public void behave(TestBehavContext context) {
        // NOOP
    }
}
