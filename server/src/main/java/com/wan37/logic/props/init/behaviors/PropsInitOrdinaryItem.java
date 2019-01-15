package com.wan37.logic.props.init.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.props.init.PropsInitBehavior;
import com.wan37.logic.props.init.PropsInitContext;
import org.springframework.stereotype.Service;

/**
 * 2：普通物品初始化
 */
@Service
@BehaviorLogic(id = 2)
class PropsInitOrdinaryItem implements PropsInitBehavior {

    @Override
    public void behave(PropsInitContext context) {
        // NOOP
    }
}
