package com.wan37.logic.player.init;

import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.ISkill;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.config.SkillCfgLoader;
import com.wan37.logic.skill.database.PSkillDb;
import com.wan37.logic.trade.entity.ITradeImpl;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PlayerCreator {

    @Autowired
    private ISkill.Factory iSkillFactory;

    @Autowired
    private SkillCfgLoader skillCfgLoader;

    @Autowired
    private IMission.Factory missionFactory;

    public Player create(PlayerDb playerDb, Channel channel) {
        Player player = new Player();
        player.setPlayerDb(playerDb);
        player.setChannel(channel);

        player.setSkills(playerDb.getPlayerSkillDb().getSkills().values().stream()
                .map(this::createSkill)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ISkill::getId, Function.identity())));

        // FIXME: 缓存过期会清掉
        player.setTrade(new ITradeImpl(null, new ReentrantLock()));

        player.setMission(missionFactory.create(playerDb.getMissionDb()));
        return player;
    }

    private ISkill createSkill(PSkillDb skillDb) {
        SkillCfg skillCfg = skillCfgLoader.load(skillDb.getCfgId()).orElse(null);
        if (skillCfg == null) {
            return null;
        }

        return iSkillFactory.create(skillCfg, skillDb);
    }
}
