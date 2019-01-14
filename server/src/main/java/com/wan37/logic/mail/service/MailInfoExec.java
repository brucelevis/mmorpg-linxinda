package com.wan37.logic.mail.service;

import com.wan37.behavior.BehaviorManager;
import com.wan37.config.ConfigLoader;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehaviorContext;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavior;
import com.wan37.logic.mail.database.MailDb;
import com.wan37.logic.mail.database.MailItemDb;
import com.wan37.logic.mail.database.MailRewardDb;
import com.wan37.logic.mail.database.MailRewardItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class MailInfoExec {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private BehaviorManager behaviorManager;

    public void exec(Player player) {
        String head = "邮件列表如下：\n";
        String content = player.getPlayerDb().getMails().stream()
                .map(this::encodeMail)
                .collect(Collectors.joining("\n------------------------------------------------------------------\n"));

        player.syncClient(head + content);
    }

    private String encodeMail(MailDb mailDb) {
        MailItemDb mailItemDb = mailDb.getMailItemDb();
        MailRewardDb mailRewardDb = mailDb.getMailRewardDb();
        String receiveTip = (mailItemDb != null && !mailDb.isHadReceived()) ||
                (mailRewardDb != null && !mailDb.isHadReceived()) ? "（附件未领取）" : "";

        String expireTime = DateTimeUtils.toLocalDateTime(mailDb.getExpireTime()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String head = String.format("主题（id：%s）：%s %s\n发件人：%s  过期日期：%s\n内容：%s", mailDb.getId(),
                mailDb.getTitle(), receiveTip, mailDb.getFromName(), expireTime, mailDb.getContent());

        String items = encodeItems(mailItemDb);
        String rewards = encodeRewards(mailRewardDb);
        return head + items + rewards;
    }

    private String encodeItems(MailItemDb itemDb) {
        if (itemDb == null) {
            return "";
        }

        String head = "\n\n道具附件：\n";
        String msg = itemDb.getItems().stream()
                .map(this::encodeItem)
                .collect(Collectors.joining("\n"));

        return head + msg;
    }

    private String encodeItem(ItemDb itemDb) {
        PropsCfg propsCfg = configLoader.load(PropsCfg.class, itemDb.getCfgId()).orElse(null);
        if (propsCfg == null) {
            return "";
        }

        Integer type = propsCfg.getType();
        ItemExtraEncodeBehavior behavior = (ItemExtraEncodeBehavior) behaviorManager.get(ItemExtraEncodeBehavior.class, type);

        ItemExtraEncodeBehaviorContext ctx = new ItemExtraEncodeBehaviorContext(itemDb.getCfgId(), itemDb.getExtraDb());
        behavior.behave(ctx);

        return String.format("名字：%s，数量：%s %s", propsCfg.getName(), itemDb.getAmount(), ctx.getResult());
    }

    private String encodeRewards(MailRewardDb rewardDb) {
        if (rewardDb == null) {
            return "";
        }

        String head = "\n\n奖励道具附件：\n";
        String msg = rewardDb.getRewards().stream()
                .map(this::encodeReward)
                .collect(Collectors.joining("\n"));

        return head + msg;
    }

    private String encodeReward(MailRewardItemDb itemDb) {
        Integer cfgId = itemDb.getCfgId();
        String name = cfgId < 200 ? configLoader.loadName(VirtualItemCfg.class, cfgId) : configLoader.loadName(PropsCfg.class, cfgId);

        return String.format("%s × %s", name, itemDb.getAmount());
    }
}
