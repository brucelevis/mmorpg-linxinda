package com.wan37.logic.mail.gm;

import java.util.List;

/**
 * Gm管理员邮件
 *
 * @author linda
 */
public interface GmMail {

    /**
     * 发送者名字
     *
     * @return String
     */
    String getFromName();

    /**
     * 邮件标题
     *
     * @return String
     */
    String getTitle();

    /**
     * 邮件内容
     *
     * @return String
     */
    String getContent();

    /**
     * 过期时间
     *
     * @return long
     */
    long getExpireTime();

    /**
     * 接受者uid
     *
     * @return Long
     */
    Long getToPlayerUid();

    /**
     * 邮件Gm奖励
     *
     * @return List<GmMailReward>
     */
    List<GmMailReward> getRewards();
}
