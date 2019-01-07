package com.wan37.logic.mail.gm;

/**
 * Gm邮件奖励
 *
 * @author linda
 */
public interface GmMailReward {

    /**
     * 物品（实物或虚物）id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 数量
     *
     * @return long
     */
    long getAmount();
}
