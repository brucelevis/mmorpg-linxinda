package com.wan37.logic.mail.database;

/**
 * 玩家邮件奖励物品数据库实体类
 *
 * @author linda
 */
public class MailRewardItemDb {

    private Integer cfgId;
    private long amount;

    public Integer getCfgId() {
        return cfgId;
    }

    public void setCfgId(Integer cfgId) {
        this.cfgId = cfgId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
