package com.wan37.logic.scene.service.neighbor;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SceneNeighborExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    public void exec(String channelId) {
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            return;
        }

        SceneCfg sceneCfg = sceneCfgLoader.load(player.getSceneId()).orElse(null);
        if (sceneCfg == null) {
            return;
        }

        String msg = encode(sceneCfg);
        player.syncClient(msg);
    }

    private String encode(SceneCfg sceneCfg) {
        String sceneHead = String.format("当前场景：%s (sceneCfgId：%s)，可达场景：\n", sceneCfg.getName(), sceneCfg.getId());

        String sceneNeighbor = sceneCfg.getNeighbor().stream()
                .map(i -> sceneCfgLoader.load(i).orElse(null))
                .filter(Objects::nonNull)
                .map(this::encodeScene)
                .collect(Collectors.joining());

        return sceneHead + sceneNeighbor;
    }

    private String encodeScene(SceneCfg sceneCfg) {
        return String.format("名字：%s (sceneCfgId：%s)\n", sceneCfg.getName(), sceneCfg.getId());
    }
}