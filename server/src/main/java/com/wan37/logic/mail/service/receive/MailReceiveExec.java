package com.wan37.logic.mail.service.receive;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.mail.database.MailDb;
import com.wan37.logic.mail.database.MailItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MailReceiveExec {

    @Autowired
    private BackpackFacade backpackFacade;

    public void exec(Player player, Long id) {
        PlayerDb playerDb = player.getPlayerDb();
        MailDb mailDb = playerDb.getMails().stream()
                .filter(m -> Objects.equals(m.getId(), id))
                .findAny()
                .orElse(null);

        if (mailDb == null) {
            player.syncClient("找不到对应邮件");
            return;
        }

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        if (now >= mailDb.getExpireTime()) {
            player.syncClient("邮件已过期");
            return;
        }

        MailItemDb mailItemDb = mailDb.getMailItemDb();
        if (mailItemDb == null) {
            player.syncClient("没有可领取的附件");
            return;
        }

        if (mailDb.isHadReceived()) {
            player.syncClient("不可重复领取");
            return;
        }

        BackpackDb backpackDb = playerDb.getBackpackDb();
        int extraCapacity = backpackDb.getCapacity() - backpackDb.getItemMap().size();
        List<ItemDb> items = mailItemDb.getItems();
        if (extraCapacity < items.size()) {
            player.syncClient("背包空位不足，请整理");
            return;
        }

        backpackFacade.add(player, items);
        mailDb.setHadReceived(true);
    }
}
