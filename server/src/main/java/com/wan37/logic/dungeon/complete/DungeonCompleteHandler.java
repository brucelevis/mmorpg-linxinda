package com.wan37.logic.dungeon.complete;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.chat.ChatFacade;
import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.config.DungeonRewardCfg;
import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.mail.gm.GmMail;
import com.wan37.logic.mail.gm.MailGmSender;
import com.wan37.logic.mail.gm.impl.GmMailImpl;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.scene.scene.SceneFacade;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DungeonCompleteHandler {

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    @Autowired
    private ChatFacade chatFacade;

    @Autowired
    private MailGmSender mailGmSender;

    public void handle(DungeonScene scene) {
        DungeonCfg dungeonCfg = scene.getDungeonCfg();
        ResourceCollection reward = randRewards(dungeonCfg);
        String msg = String.format("获得奖励：%s\n", encodeRewards(reward));

        scene.getPlayers().forEach(p -> completeImpl(p, reward, dungeonCfg, msg));

        temporarySceneGlobalManager.destoryScene(scene.getUid());
    }

    private String encodeRewards(ResourceCollection reward) {
        return reward.getElements().stream()
                .map(this::encodeReward)
                .collect(Collectors.joining("，"));
    }

    private String encodeReward(ResourceElement e) {
        String name = e.getCfgId() < 200 ? virtualItemCfgLoader.getName(e.getCfgId()) : propsCfgLoader.getName(e.getCfgId());
        return String.format("%s × %s", name, e.getAmount());
    }

    private ResourceCollection randRewards(DungeonCfg cfg) {
        return new ResourceCollectionImpl(cfg.getReward().stream()
                .map(this::randReward)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    private ResourceElement randReward(DungeonRewardCfg cfg) {
        if (!RandomUtil.isHit(cfg.getProbability())) {
            return null;
        }

        return new ResourceElementImpl(cfg.getId(), cfg.getAmount());
    }

    private void completeImpl(Player player, ResourceCollection reward, DungeonCfg dungeonCfg, String rewardTip) {
        String worldNotify = String.format(dungeonCfg.getCompleteTip(), player.getName(), dungeonCfg.getName());
        chatFacade.chatToWorld(worldNotify);

        player.syncClient(rewardTip);

        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        int spareCapacity = backpackDb.getCapacity() - backpackDb.getItemMap().size();
        if (spareCapacity >= reward.getElements().size()) {
            resourceFacade.giveResource(reward, player);
        } else {
            // 发邮件
            String title = String.format("%s奖励", dungeonCfg.getName());
            String content = String.format("英勇的%s，恭喜你通关%s", player.getName(), dungeonCfg.getName());

            GmMail gmMail = createGmMail(title, content, player.getUid(), reward);
            mailGmSender.send(gmMail);
        }

        // 回到安全村
        sceneFacade.enterScene(1000, player);
    }

    private GmMail createGmMail(String title, String content, Long toPlayerUid, ResourceCollection rewards) {
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        long expireTime = DateTimeUtils.toEpochMilli(today_end) + TimeUnit.DAYS.toMillis(7);

        return new GmMailImpl("Gm管理员", title, content, expireTime, toPlayerUid, rewards);
    }
}
