package com.wan37.logic.mail.database.convert;

import com.wan37.util.JsonUtil;
import com.wan37.logic.mail.database.MailRewardDb;

import javax.persistence.AttributeConverter;

public class MailRewardDbConverterImpl implements AttributeConverter<MailRewardDb, String> {

    @Override
    public String convertToDatabaseColumn(MailRewardDb mailRewardDb) {
        return JsonUtil.parseJson(mailRewardDb);
    }

    @Override
    public MailRewardDb convertToEntityAttribute(String s) {
        return (MailRewardDb) JsonUtil.parseObj(s, MailRewardDb.class);
    }
}
