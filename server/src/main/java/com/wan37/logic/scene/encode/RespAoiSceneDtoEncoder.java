package com.wan37.logic.scene.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.resp.RespAoiSceneDto;
import com.wan37.common.resp.RespAoiSceneMonsterDto;
import com.wan37.common.resp.RespAoiSceneNpcDto;
import com.wan37.common.resp.RespAoiScenePlayerDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.npc.Npc;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.Scene;
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
        dto.setSceneId(scene.getId());

        dto.setPlayers(scene.getPlayers().stream()
                .map(this::encodePlayers)
                .collect(Collectors.toList()));

        dto.setMonsters(scene.getMonsters().stream()
                .map(this::encodeMonster)
                .collect(Collectors.toList()));

        dto.setNpcs(scene.getNpcs().stream()
                .map(this::encodeNpc)
                .collect(Collectors.toList()));

        return dto;
    }

    private RespAoiSceneNpcDto encodeNpc(Npc npc) {
        RespAoiSceneNpcDto dto = new RespAoiSceneNpcDto();
        dto.setCfgId(npc.getCfgId());
        dto.setName(npc.getName());
        return dto;
    }

    private RespAoiSceneMonsterDto encodeMonster(Monster monster) {
        RespAoiSceneMonsterDto dto = new RespAoiSceneMonsterDto();
        dto.setUid(monster.getUid());
        dto.setName(monster.getName());
        return dto;
    }

    private RespAoiScenePlayerDto encodePlayers(Player player) {
        RespAoiScenePlayerDto dto = new RespAoiScenePlayerDto();
        dto.setPlayerUid(player.getUid());
        dto.setFactionId(player.getFactionId());
        dto.setName(player.getName());
        dto.setLevel(player.getLevel());
        return dto;
    }
}
