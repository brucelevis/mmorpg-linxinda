package com.wan37.logic.mail.gm;

import com.wan37.logic.mail.dao.MailDao;
import com.wan37.logic.mail.database.MailDb;
import com.wan37.logic.mail.database.MailRewardDb;
import com.wan37.logic.mail.database.MailRewardItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailGmSender {

    @Autowired
    private IdTool idTool;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private MailDao mailDao;

    public void send(GmMail mail) {
        MailDb mailDb = createMail(mail);

        Long uid = mail.getToPlayerUid();
        Player to = playerGlobalManager.getPlayerByUid(uid);
        if (to != null) {
            // 对方数据在缓存，直接添加邮件信息
            to.getPlayerDb().addMail(mailDb);

            if (playerGlobalManager.isOnline(uid)) {
                // 如果在线，推送通知
                String msg = String.format("收到来自%s的邮件", mail.getFromName());
                to.syncClient(msg);
            }

            return;
        }

        // 对方不在线
        mailDao.save(mailDb);
    }

    private MailDb createMail(GmMail mail) {
        MailDb mailDb = new MailDb();
        mailDb.setId(idTool.generate());
        mailDb.setHadReceived(false);
        mailDb.setTitle(mail.getTitle());
        mailDb.setContent(mail.getContent());
        mailDb.setExpireTime(mail.getExpireTime());
        mailDb.setFromName(mail.getFromName());

        // 添加系统奖励道具附件
        MailRewardDb mailRewardDb = createMailReward(mail.getRewards());
        mailDb.setMailRewardDb(mailRewardDb);

        PlayerDb to = new PlayerDb();
        to.setUid(mail.getToPlayerUid());
        mailDb.setPlayer(to);

        return mailDb;
    }

    private MailRewardDb createMailReward(List<GmMailReward> rewards) {
        MailRewardDb db = new MailRewardDb();
        db.setRewards(rewards.stream()
                .map(this::createItem)
                .collect(Collectors.toList()));
        return db;
    }

    private MailRewardItemDb createItem(GmMailReward reward) {
        MailRewardItemDb db = new MailRewardItemDb();
        db.setCfgId(reward.getId());
        db.setAmount(reward.getAmount());
        return db;
    }
}