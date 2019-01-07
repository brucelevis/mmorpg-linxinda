package com.wan37.logic.mail.service.send;

/**
 * 请求发送的邮件附件
 *
 * @author linda
 */
public interface ReqSendMailItem {

    /**
     * 指定背包格子
     *
     * @return Integer
     */
    Integer getIndex();

    /**
     * 数量
     *
     * @return int
     */
    int getAmount();
}
