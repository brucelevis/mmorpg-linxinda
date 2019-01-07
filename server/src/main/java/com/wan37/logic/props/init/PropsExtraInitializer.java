package com.wan37.logic.props.init;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 初始化物品额外信息
 *
 * @author linda
 */
@Service
public class PropsExtraInitializer {

    @Autowired
    private BehaviorManager behaviorManager;

    public Object init(PropsCfg propsCfg) {
        PropsInitBehavior behavior = (PropsInitBehavior) behaviorManager.get(PropsInitBehavior.class, propsCfg.getType());
        PropsInitContext ctx = new PropsInitContext(propsCfg);
        behavior.behave(ctx);

        return ctx.getExtraDb();
    }
}
