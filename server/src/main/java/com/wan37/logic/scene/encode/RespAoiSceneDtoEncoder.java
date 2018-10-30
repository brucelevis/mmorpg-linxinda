package com.wan37.logic.scene.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.resp.RespAoiSceneDto;
import com.wan37.common.resp.RespAoiScenePlayerDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.player.ScenePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RespAoiSceneDtoEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, Scene scene) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(scene));
        return dto;
    }

    private RespAoiSceneDto encodeContent(Scene scene) {
        RespAoiSceneDto dto = new RespAoiSceneDto();
        dto.setSceneId(scene.getSceneId());

        dto.setPlayers(scene.getPlayers().stream()
                .map(this::encodePlayers)
                .collect(Collectors.toList()));

        return dto;
    }

    private RespAoiScenePlayerDto encodePlayers(ScenePlayer player) {
        RespAoiScenePlayerDto dto = new RespAoiScenePlayerDto();
        dto.setPlayerUid(player.getPlayerUid());
        dto.setFactionId(player.getFactionId());
        dto.setName(player.getPlayerName());
        dto.setLevel(player.getLevel());
        return dto;
    }
}
