package com.wan37.common.resp;

import java.util.List;

public class RespAoiSceneDto {

    private Integer sceneId;

    private List<RespAoiScenePlayerDto> players;

    private List<RespAoiSceneNpcDto> npcs;

    private List<RespAoiSceneMonsterDto> monsters;

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public List<RespAoiScenePlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<RespAoiScenePlayerDto> players) {
        this.players = players;
    }

    public List<RespAoiSceneNpcDto> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<RespAoiSceneNpcDto> npcs) {
        this.npcs = npcs;
    }

    public List<RespAoiSceneMonsterDto> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<RespAoiSceneMonsterDto> monsters) {
        this.monsters = monsters;
    }
}
