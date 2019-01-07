package com.wan37.logic.mail.database;

import java.util.List;

/**
 * 玩家邮件奖励数据库实体类
 *
 * @author linda
 */
public class MailRewardDb {

    private List<MailRewardItemDb> rewards;

    public List<MailRewardItemDb> getRewards() {
        return rewards;
    }

    public void setRewards(List<MailRewardItemDb> rewards) {
        this.rewards = rewards;
    }
}
