package com.wan37.logic.scene.schedule;

import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.encode.SceneItemEncoder;
import com.wan37.logic.scene.base.SceneItem;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 场景物品刷新检查定时器逻辑
 *
 * @author linda
 */
@Service
public class SceneItemScheduler {

    @Autowired
    private SceneItemEncoder sceneItemEncoder;

    public void schedule(AbstractScene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        Map<Long, SceneItem> sceneItems = scene.getItems();

        // 过期物品
        List<SceneItem> expireItems = sceneItems.values().stream()
                .filter(i -> i.getExpireTime() <= now)
                .collect(Collectors.toList());

        if (expireItems.isEmpty()) {
            return;
        }

        // 移除
        expireItems.forEach(i -> sceneItems.remove(i.getUid()));

        String head = "场景物品过期消失推送：\n";
        String msg = expireItems.stream()
                .map(i -> sceneItemEncoder.encode(i))
                .collect(Collectors.joining("\n"));

        // 推送给场景玩家
        scene.getPlayers().forEach(p -> p.syncClient(head + msg));
    }
}
