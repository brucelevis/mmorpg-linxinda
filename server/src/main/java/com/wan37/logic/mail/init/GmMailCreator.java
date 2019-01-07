package com.wan37.logic.mail.init;

import com.wan37.logic.mail.gm.GmMail;
import com.wan37.logic.mail.gm.impl.GmMailImpl;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.util.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * Gm邮件生成类
 *
 * @author linda
 */
@Service
public class GmMailCreator {

    public GmMail create(String title, String content, Long toPlayerUid, ResourceCollection rewards) {
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        long expireTime = DateTimeUtils.toEpochMilli(todayEnd) + TimeUnit.DAYS.toMillis(7);

        return new GmMailImpl("Gm管理员", title, content, expireTime, toPlayerUid, rewards);
    }
}
