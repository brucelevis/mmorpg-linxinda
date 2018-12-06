package com.wan37.logic.mail.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.LoginEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.util.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
class MailOnPlayerLogin implements GeneralEventListener<LoginEvent> {

    @Override
    public void execute(LoginEvent event) {
        Player player = event.getPlayer();
        PlayerDb playerDb = player.getPlayerDb();

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        playerDb.setMails(playerDb.getMails().stream()
                .filter(m -> m.getExpireTime() > now)
                .collect(Collectors.toSet()));
    }
}
