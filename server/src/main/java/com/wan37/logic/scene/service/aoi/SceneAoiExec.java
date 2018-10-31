package com.wan37.logic.scene.service.aoi;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.encode.RespAoiSceneDtoEncoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SceneAoiExec {

    private static final Logger LOG = Logger.getLogger(SceneAoiExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private RespAoiSceneDtoEncoder respAoiSceneDtoEncoder;

    public void exec(SAoiScene aoiScene) {
        String channelId = aoiScene.getChannel().id().asLongText();
        Optional<Player> optionalPlayer = playerGlobalManager.findPlayerByChannelId(channelId);
        if (!optionalPlayer.isPresent()) {
            LOG.info("角色未登录，不允许发送该命令");
        }

        Integer sceneId = optionalPlayer.get().getSceneId();
        Scene scene = sceneGlobalManager.getScene(sceneId);

        GeneralResponseDto dto = respAoiSceneDtoEncoder.encode(ResultCode.SCENE_AOI, scene);
        aoiScene.response(dto);
    }
}
