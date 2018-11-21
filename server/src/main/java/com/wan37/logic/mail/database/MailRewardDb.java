package com.wan37.logic.mail.database;

import java.util.List;

public class MailRewardDb {

    private List<MailRewardItemDb> rewards;

    public List<MailRewardItemDb> getRewards() {
        return rewards;
    }

    public void setRewards(List<MailRewardItemDb> rewards) {
        this.rewards = rewards;
    }
}
