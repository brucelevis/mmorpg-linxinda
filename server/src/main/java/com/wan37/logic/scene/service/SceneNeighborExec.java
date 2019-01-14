package com.wan37.logic.scene.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.config.SceneCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class SceneNeighborExec {

    @Autowired
    private ConfigLoader configLoader;

    public void exec(Player player) {
        SceneCfg sceneCfg = configLoader.load(SceneCfg.class, player.getSceneId()).orElse(null);
        if (sceneCfg == null) {
            return;
        }

        String msg = encode(sceneCfg);
        player.syncClient(msg);
    }

    private String encode(SceneCfg sceneCfg) {
        String sceneHead = String.format("当前场景：%s (sceneCfgId：%s)，可达场景：\n", sceneCfg.getName(), sceneCfg.getId());

        String sceneNeighbor = sceneCfg.getNeighbor().stream()
                .map(i -> configLoader.load(SceneCfg.class, i).orElse(null))
                .filter(Objects::nonNull)
                .map(this::encodeScene)
                .collect(Collectors.joining("\n"));

        return sceneHead + sceneNeighbor;
    }

    private String encodeScene(SceneCfg sceneCfg) {
        return String.format("名字：%s (sceneCfgId：%s)", sceneCfg.getName(), sceneCfg.getId());
    }
}
