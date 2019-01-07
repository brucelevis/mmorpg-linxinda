package com.wan37.logic.mission.complete;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.MissionCompleteEvent;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.mail.gm.GmMail;
import com.wan37.logic.mail.gm.MailGmSender;
import com.wan37.logic.mail.init.GmMailCreator;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.config.MissionRewardCfg;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.mission.service.accept.MissionAccepter;
import com.wan37.logic.npc.config.NpcCfgLoader;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.service.PlayerExpAdder;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * 任务完成处理类
 *
 * @author linda
 */
@Service
public class MissionCompleteHandler {

    @Autowired
    private MailGmSender mailGmSender;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private GmMailCreator gmMailCreator;

    @Autowired
    private PlayerExpAdder playerExpAdder;

    @Autowired
    private NpcCfgLoader npcCfgLoader;

    @Autowired
    private MissionAccepter missionAccepter;

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void handle(Player player, PlayerMission playerMission) {
        playerMission.setCanComplete(false);
        playerMission.setCompleteTime(DateTimeUtils.toEpochMilli(LocalDateTime.now()));

        MissionCfg missionCfg = playerMission.getMissionCfg();
        String npcName = npcCfgLoader.loadName(missionCfg.getNpcId());
        String msg = String.format("【%s】 %s", npcName, missionCfg.getCompleteTip());
        player.syncClient(msg);

        // 发放奖励
        ResourceCollection rewards = getRewards(missionCfg);
        if (backpackFacade.getSpareCapacity(player) >= rewards.getElements().size()) {
            //FIXME: 这里先简单的判断奖励数量，里面包含虚物后续优化处理
            resourceFacade.giveResource(rewards, player);
        } else {
            // 发邮件
            String title = String.format("[%s]任务奖励", missionCfg.getName());

            GmMail gmMail = gmMailCreator.create(title, title, player.getUid(), rewards);
            mailGmSender.send(gmMail);
        }

        // 奖励经验
        playerExpAdder.add(player, missionCfg.getExp());

        if (missionCfg.getNextId() != null) {
            // 有下一个任务
            MissionCfg nextMissionCfg = missionCfgLoader.load(missionCfg.getNextId())
                    .orElseThrow(() -> new RuntimeException("策划任务导表配置出错"));
            missionAccepter.accept(player, nextMissionCfg);
        }

        // 抛出任务完成事件
        generalEventListenersManager.fireEvent(new MissionCompleteEvent(player));
    }

    private ResourceCollection getRewards(MissionCfg missionCfg) {
        return new ResourceCollectionImpl(missionCfg.getReward().stream()
                .map(this::getReward)
                .collect(Collectors.toList()));
    }

    private ResourceElement getReward(MissionRewardCfg rewardCfg) {
        return new ResourceElementImpl(rewardCfg.getId(), rewardCfg.getAmount());
    }
}
