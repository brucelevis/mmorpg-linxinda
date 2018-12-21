package com.wan37.logic.mission.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.mail.gm.GmMail;
import com.wan37.logic.mail.gm.MailGmSender;
import com.wan37.logic.mail.init.GmMailCreator;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionRewardCfg;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.npc.config.NpcCfgLoader;
import com.wan37.logic.player.Player;
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

@Service
public class MissionCommitExec {

    @Autowired
    private NpcCfgLoader npcCfgLoader;

    @Autowired
    private MailGmSender mailGmSender;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private GmMailCreator gmMailCreator;

    public void exec(Player player, Integer missionId) {
        IMission iMission = player.getMission();
        if (!iMission.isProceeding(missionId)) {
            throw new GeneralErrorExecption("没在进行中的任务");
        }

        IPlayerMission playerMission = iMission.getPlayerMission(missionId);
        MissionCfg missionCfg = playerMission.getMissionCfg();

        String npcName = npcCfgLoader.loadName(missionCfg.getNpcId());
        if (!playerMission.canComplete()) {
            // 未完成
            String msg = String.format("【%s】 %s", npcName, missionCfg.getProceedTip());
            player.syncClient(msg);
            return;
        }

        playerMission.setCanComplete(false);
        playerMission.setCompleteTime(DateTimeUtils.toEpochMilli(LocalDateTime.now()));

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
