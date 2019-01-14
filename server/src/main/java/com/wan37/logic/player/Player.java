package com.wan37.logic.player;


import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PlayerEachAttrDb;
import com.wan37.logic.buff.entity.Buff;
import com.wan37.logic.mission.entity.Mission;
import com.wan37.logic.pk.entity.Pk;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.entity.Skill;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.util.GeneralNotifySenderUtil;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 玩家实例
 *
 * @author linda
 */
public class Player implements FightingUnit {

    private Channel channel;
    private PlayerDb playerDb;

    private Map<Integer, Skill> skills;
    private List<Buff> buffs = new CopyOnWriteArrayList<>();
    private ITrade trade;
    private Mission mission;
    private Pk pk;

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

    public void setSkills(Map<Integer, Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String getName() {
        return playerDb.getName();
    }

    public Integer getFactionId() {
        return playerDb.getFactionId();
    }

    @Override
    public int getLevel() {
        return playerDb.getLevel();
    }

    public void syncClient(String content) {
        GeneralNotifySenderUtil.send(channel, content);
    }

    public void setExp(long exp) {
        playerDb.setExp(exp);
    }

    public long getExp() {
        return playerDb.getExp();
    }

    public void setLevel(int level) {
        playerDb.setLevel(level);
    }

    public void setGuildUid(Long uid) {
        playerDb.setGuildUid(uid);
    }

    public Long getGuildUid() {
        return playerDb.getGuildUid();
    }

    public ITrade getTrade() {
        return trade;
    }

    public void setTrade(ITrade trade) {
        this.trade = trade;
    }

    public Mission getMission() {
        return mission;
    }

    public Pk getPk() {
        return pk;
    }

    public void setPk(Pk pk) {
        this.pk = pk;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void setTeamUid(Long uid) {
        playerDb.setTeamUid(uid);
    }

    public Long getTeamUid() {
        return playerDb.getTeamUid();
    }

    @Override
    public Map<Integer, Double> getAttrs() {
        return playerDb.getPlayerStrengthDb().getAttrs();
    }

    @Override
    public Map<Integer, Skill> getSkills() {
        return skills;
    }

    @Override
    public List<Buff> getBuffs() {
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
        PlayerEachAttrDb playerEachAttrDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_HP.getId());
        return playerEachAttrDb == null ? 0 : Math.round(playerEachAttrDb.getValue());
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
        PlayerEachAttrDb playerEachAttrDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_MP.getId());
        return playerEachAttrDb == null ? 0 : Math.round(playerEachAttrDb.getValue());
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
