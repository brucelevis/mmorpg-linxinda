package com.wan37.logic.player;

import com.wan37.logic.trade.entity.ITrade;

public interface IPlayer {

    Integer getFactionId();

    void syncClient(String content);

    void setExp(long exp);

    long getExp();

    void setlevel(int level);

    void setLeagueUid(Long uid);

    Long getLeagueUid();

    ITrade getTrade();

    void setTeamUid(Long uid);

    Long getTeamUid();
}
