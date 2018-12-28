package com.wan37.logic.mail.service;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.mail.database.MailDb;
import com.wan37.logic.mail.database.MailItemDb;
import com.wan37.logic.mail.database.MailRewardDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MailReceiveExec {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private ResourceFacade resourceFacade;

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

        if (mailDb.isHadReceived()) {
            player.syncClient("不可重复领取");
            return;
        }

        MailItemDb mailItemDb = mailDb.getMailItemDb();
        MailRewardDb mailRewardDb = mailDb.getMailRewardDb();
        if (mailItemDb == null && mailRewardDb == null) {
            player.syncClient("没有可领取的附件");
            return;
        }

        if (mailItemDb != null) {
            // 玩家背包附件
            int extraCapacity = backpackFacade.getSpareCapacity(player);
            List<ItemDb> items = mailItemDb.getItems();
            if (extraCapacity < items.size()) {
                player.syncClient("背包空位不足，请整理");
                return;
            }

            backpackFacade.add(player, items);
        }

        if (mailRewardDb != null) {
            // 系统奖励附件
            ResourceCollection rewards = new ResourceCollectionImpl(mailRewardDb.getRewards().stream()
                    .map(r -> new ResourceElementImpl(r.getCfgId(), r.getAmount()))
                    .collect(Collectors.toList()));

            resourceFacade.giveResource(rewards, player);
        }

        mailDb.setHadReceived(true);
    }
}
