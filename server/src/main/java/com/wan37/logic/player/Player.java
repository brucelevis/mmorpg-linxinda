package com.wan37.logic.player;


import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.buff.entity.IBuff;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.pk.entity.IPk;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.entity.ISkill;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.util.GeneralNotifySenderUtil;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


public class Player implements FightingUnit, IPlayer {

    private Channel channel;
    private PlayerDb playerDb;

    private Map<Integer, ISkill> skills;
    private List<IBuff> buffs = new CopyOnWriteArrayList<>();
    private ITrade trade;
    private IMission mission;
    private IPk pk;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public PlayerDb getPlayerDb() {
        return playerDb;
    }

    public void setPlayerDb(PlayerDb playerDb) {
        this.playerDb = playerDb;
    }

    public void setSkills(Map<Integer, ISkill> skills) {
        this.skills = skills;
    }

    public void setBuffs(List<IBuff> buffs) {
        this.buffs = buffs;
    }

    @Override
    public String getName() {
        return playerDb.getName();
    }

    @Override
    public Integer getFactionId() {
        return playerDb.getFactionId();
    }

    @Override
    public int getLevel() {
        return playerDb.getLevel();
    }

    @Override
    public void syncClient(String content) {
        GeneralNotifySenderUtil.send(channel, content);
    }

    @Override
    public void setExp(long exp) {
        playerDb.setExp(exp);
    }

    @Override
    public long getExp() {
        return playerDb.getExp();
    }

    @Override
    public void setlevel(int level) {
        playerDb.setLevel(level);
    }

    @Override
    public void setLeagueUid(Long uid) {
        playerDb.setLeagueUid(uid);
    }

    @Override
    public Long getLeagueUid() {
        return playerDb.getLeagueUid();
    }

    @Override
    public ITrade getTrade() {
        return trade;
    }

    public void setTrade(ITrade trade) {
        this.trade = trade;
    }

    @Override
    public IMission getMission() {
        return mission;
    }

    @Override
    public IPk getPk() {
        return pk;
    }

    public void setPk(IPk pk) {
        this.pk = pk;
    }

    public void setMission(IMission mission) {
        this.mission = mission;
    }

    @Override
    public void setTeamUid(Long uid) {
        playerDb.setTeamUid(uid);
    }

    @Override
    public Long getTeamUid() {
        return playerDb.getTeamUid();
    }

    @Override
    public Map<Integer, Double> getAttrs() {
        return playerDb.getPlayerStrengthDb().getAttrs();
    }

    @Override
    public Map<Integer, ISkill> getSkills() {
        return skills;
    }

    @Override
    public List<IBuff> getBuffs() {
        return buffs;
    }

    @Override
    public long getBaseAttackVal() {
        return playerDb.getPlayerStrengthDb().getBaseAttackVal();
    }

    @Override
    public long getBaseDefenseVal() {
        return playerDb.getPlayerStrengthDb().getBaseDefenseVal();
    }

    @Override
    public Long getUid() {
        return playerDb.getUid();
    }

    @Override
    public Integer getSceneId() {
        return playerDb.getSceneId();
    }

    @Override
    public void setSceneId(Integer sceneId) {
        playerDb.setSceneId(sceneId);
    }

    @Override
    public Long getSceneUid() {
        return playerDb.getSceneUid();
    }

    @Override
    public void setSceneUid(Long uid) {
        playerDb.setSceneUid(uid);
    }

    @Override
    public long getHp() {
        return playerDb.getHp();
    }

    @Override
    public void setHp(long hp) {
        playerDb.setHp(hp);
    }

    @Override
    public long getMaxHp() {
        PAttrDb pAttrDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_HP.getId());
        return pAttrDb == null ? 0 : Math.round(pAttrDb.getValue());
    }

    @Override
    public long getMp() {
        return playerDb.getMp();
    }

    @Override
    public void setMp(long mp) {
        playerDb.setMp(mp);
    }

    @Override
    public long getMaxMp() {
        PAttrDb pAttrDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_MP.getId());
        return pAttrDb == null ? 0 : Math.round(pAttrDb.getValue());
    }

    @Override
    public long getDeadTime() {
        return playerDb.getDeadTime();
    }

    @Override
    public void setDeadTime(long time) {
        playerDb.setDeadTime(time);
    }

    @Override
    public boolean isAlive() {
        return playerDb.isAlive();
    }

    @Override
    public void setAlive(boolean alive) {
        playerDb.setAlive(alive);
    }
}
