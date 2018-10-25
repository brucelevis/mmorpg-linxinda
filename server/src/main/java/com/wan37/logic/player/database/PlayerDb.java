package com.wan37.logic.player.database;

public class PlayerDb {

    private Long uid;

    /**
     * 玩家账号
     */
    private long account;

    /**
     * 玩家密码
     */
    private String password;

    /**
     * 门派id
     */
    private Integer factionId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFactionId() {
        return factionId;
    }

    public void setFactionId(Integer factionId) {
        this.factionId = factionId;
    }
}
