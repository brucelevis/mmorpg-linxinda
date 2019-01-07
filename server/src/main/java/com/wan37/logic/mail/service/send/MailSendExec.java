package com.wan37.logic.mail.service.send;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.mail.dao.MailDao;
import com.wan37.logic.mail.database.MailDb;
import com.wan37.logic.mail.database.MailItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class MailSendExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private MailDao mailDao;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    public void exec(ReqSendMail mail) {
        Player from = mail.getFromPlayer();

        Long uid = mail.getToUid();
        MailDb mailDb = createMail(mail);

        Player to = playerGlobalManager.getPlayerIfPresent(uid);
        if (to != null) {
            // 对方数据在缓存，直接添加邮件信息
            to.getPlayerDb().addMail(mailDb);

            if (playerGlobalManager.isOnline(uid)) {
                // 如果在线，推送通知
                String msg = String.format("收到来自%s的邮件", from.getName());
                to.syncClient(msg);
            }

            afterSend(from);
            return;
        }

        // 对方不在线
        mailDao.save(mailDb);
        afterSend(from);
    }

    private MailDb createMail(ReqSendMail mail) {
        MailDb mailDb = new MailDb();
        mailDb.setId(IdUtil.generate());
        mailDb.setTitle(mail.getTitle());
        mailDb.setContent(mail.getContent());
        mailDb.setHadReceived(false);

        //FIXME: 写死1周过期
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        mailDb.setExpireTime(DateTimeUtils.toEpochMilli(today_end) + TimeUnit.DAYS.toMillis(7));

        Player from = mail.getFromPlayer();
        mailDb.setFromName(from.getName());

        // 添加道具附件
        List<ReqSendMailItem> items = mail.getItems();
        MailItemDb mailItemDb = createMailItems(from, items);
        mailDb.setMailItemDb(mailItemDb);

        PlayerDb to = new PlayerDb();
        to.setUid(mail.getToUid());
        mailDb.setPlayer(to);

        return mailDb;
    }

    private MailItemDb createMailItems(Player player, List<ReqSendMailItem> items) {
        if (items == null) {
            return null;
        }

        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        List<Pair> pairs = items.stream()
                .map(i -> toPair(backpackDb, i))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (pairs.size() != items.size()) {
            String tip = "邮件道具附件参数有错，请检查格子索引";
            player.syncClient(tip);
            throw new RuntimeException(tip);
        }

        List<ItemDb> mailItems = new ArrayList<>();
        for (Pair pair : pairs) {
            ItemDb itemDb = pair.itemDb;
            ReqSendMailItem mailItem = pair.mailItem;

            int sendAmount = mailItem.getAmount();
            if (itemDb.getAmount() < sendAmount) {
                String tip = "想要发送的邮件附件道具数量不足";
                player.syncClient(tip);
                throw new RuntimeException(tip);
            }

            // 复制物品信息
            ItemDb newItem = new ItemDb();
            BeanUtils.copyProperties(itemDb, newItem);

            newItem.setAmount(sendAmount);
            mailItems.add(newItem);

            backpackFacade.remove(backpackDb, mailItem.getIndex(), sendAmount);
        }

        MailItemDb mailItemDb = new MailItemDb();
        mailItemDb.setItems(mailItems);
        return mailItemDb;
    }

    private Pair toPair(BackpackDb backpackDb, ReqSendMailItem mailItem) {
        ItemDb itemDb = backpackFacade.find(backpackDb, mailItem.getIndex()).orElse(null);
        if (itemDb == null) {
            return null;
        }

        Pair pair = new Pair();
        pair.itemDb = itemDb;
        pair.mailItem = mailItem;
        return pair;
    }

    private void afterSend(Player from) {
        backpackUpdateNotifier.notify(from);
        from.syncClient("邮件发送成功");
    }

    private static class Pair {

        ItemDb itemDb;

        ReqSendMailItem mailItem;
    }
}
