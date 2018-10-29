package com.wan37.logic.scene.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.login.LoginEvent;
import org.springframework.stereotype.Service;

@Service
public class SceneOnLogin implements GeneralEventListener<LoginEvent> {

    @Override
    public void execute(LoginEvent event) {
        // TODO: 通知场景玩家
        System.out.print("1");
    }
}
