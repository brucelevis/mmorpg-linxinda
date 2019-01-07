package com.wan37.logic.mail.service.send;

import com.wan37.logic.player.Player;

import java.util.List;

/**
 * 请求发送的邮件信息
 *
 * @author linda
 */
public interface ReqSendMail {

    interface Factory {

        /**
         * 创建请求发送的邮件实体
         *
         * @param player 发送者
         * @param params 参数
         * @return ReqSendMail
         */
        ReqSendMail create(Player player, String[] params);
    }

    /**
     * 发送者
     *
     * @return Player
     */
    Player getFromPlayer();

    /**
     * 接受者uid
     *
     * @return Long
     */
    Long getToUid();

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
     * 请求发送的邮件道具附件
     *
     * @return List<ReqSendMailItem>
     */
    List<ReqSendMailItem> getItems();
}
