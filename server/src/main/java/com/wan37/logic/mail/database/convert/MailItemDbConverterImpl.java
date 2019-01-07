package com.wan37.logic.mail.database.convert;

import com.wan37.util.JsonUtil;
import com.wan37.logic.mail.database.MailItemDb;

import javax.persistence.AttributeConverter;

/**
 * 邮件道具附件持久化成json到数据库
 *
 * @author linda
 */
public class MailItemDbConverterImpl implements AttributeConverter<MailItemDb, String> {

    @Override
    public String convertToDatabaseColumn(MailItemDb mailItemDb) {
        return JsonUtil.parseJson(mailItemDb);
    }

    @Override
    public MailItemDb convertToEntityAttribute(String s) {
        return (MailItemDb) JsonUtil.parseObj(s, MailItemDb.class);
    }
}
