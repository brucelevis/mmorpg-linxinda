package com.wan37.common.resp;

public class RespLoginPlayerDto {

    private Long playerUid;

    private String name;

    private int level;

    private Integer factionId;

    public Long getPlayerUid() {
        return playerUid;
    }

    public void setPlayerUid(Long playerUid) {
        this.playerUid = playerUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getFactionId() {
        return factionId;
    }

    public void setFactionId(Integer factionId) {
        this.factionId = factionId;
    }
}
