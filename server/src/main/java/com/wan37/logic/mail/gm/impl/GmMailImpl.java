package com.wan37.logic.mail.gm.impl;

import com.wan37.logic.mail.gm.GmMail;
import com.wan37.logic.mail.gm.GmMailReward;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.ResourceElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linda
 */
public class GmMailImpl implements GmMail {

    public GmMailImpl(String fromName, String title, String content, long expireTime, Long toPlayerUid, ResourceCollection rewards) {
        this.fromName = fromName;
        this.title = title;
        this.content = content;
        this.expireTime = expireTime;
        this.toPlayerUid = toPlayerUid;
        this.rewards = rewards;
    }

    @Override
    public String getFromName() {
        return fromName;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public Long getToPlayerUid() {
        return toPlayerUid;
    }

    @Override
    public List<GmMailReward> getRewards() {
        return rewards.getElements().stream()
                .map(this::createReward)
                .collect(Collectors.toList());
    }

    private GmMailReward createReward(ResourceElement e) {
        return new MailRewardImpl(e.getCfgId(), e.getAmount());
    }

    private final String fromName;
    private final String title;
    private final String content;
    private final long expireTime;
    private final Long toPlayerUid;
    private final ResourceCollection rewards;
}
