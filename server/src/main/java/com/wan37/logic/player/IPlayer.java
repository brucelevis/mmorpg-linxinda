package com.wan37.logic.player;

public interface IPlayer {

    Integer getFactionId();

    void syncClient(String content);

    void setExp(long exp);

    long getExp();

    void setlevel(int level);

    void setLeagueUid(Long uid);

    Long getLeagueUid();
}
