package com.wan37.logic.player.init;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.mission.entity.Mission;
import com.wan37.logic.pk.entity.impl.PkImpl;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.entity.Skill;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.database.PlayerEachSkillDb;
import com.wan37.logic.trade.entity.ITradeImpl;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 玩家创建逻辑
 *
 * @author linda
 */
@Service
public class PlayerCreator {

    @Autowired
    private Skill.Factory iSkillFactory;

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private Mission.Factory missionFactory;

    public Player create(PlayerDb playerDb, Channel channel) {
        Player player = new Player();
        player.setPlayerDb(playerDb);
        player.setChannel(channel);

        player.setSkills(playerDb.getPlayerSkillDb().getSkills().values().stream()
                .map(this::createSkill)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Skill::getId, Function.identity())));

        // FIXME: 缓存过期会清掉
        player.setTrade(new ITradeImpl(null, new ReentrantLock()));

        player.setMission(missionFactory.create(playerDb.getMissionDb()));
        player.setPk(new PkImpl(new ReentrantLock(), new HashMap<>(0)));
        return player;
    }

    private Skill createSkill(PlayerEachSkillDb skillDb) {
        SkillCfg skillCfg = configLoader.load(SkillCfg.class, skillDb.getCfgId()).orElse(null);
        if (skillCfg == null) {
            return null;
        }

        return iSkillFactory.create(skillCfg, skillDb);
    }
}
