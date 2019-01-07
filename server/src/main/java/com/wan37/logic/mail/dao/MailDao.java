package com.wan37.logic.mail.dao;

import com.wan37.logic.mail.database.MailDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

/**
 * 邮件系统数据库操作
 *
 * @author linda
 */
@Service
public interface MailDao extends Repository<MailDb, Long> {

    /**
     * 保存玩家邮件系统数据
     *
     * @param mailDb 玩家邮件系统数据
     */
    void save(MailDb mailDb);
}
