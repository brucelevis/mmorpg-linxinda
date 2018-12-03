package com.wan37.logic.mail.gm;

import java.util.List;

public interface GmMail {

    String getFromName();

    String getTitle();

    String getContent();

    long getExpireTime();

    Long getToPlayerUid();

    List<GmMailReward> getRewards();
}
