package com.wan37.logic.mail.dao;

import com.wan37.logic.mail.database.MailDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public interface MailDao extends Repository<MailDb, Long> {

    void save(MailDb mailDb);
}
