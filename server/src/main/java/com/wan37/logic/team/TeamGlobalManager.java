package com.wan37.logic.team;

import com.wan37.logic.team.entity.ITeam;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TeamGlobalManager {

    /**
     * key：teamUid
     */
    private static Map<Long, ITeam> teamMap = new ConcurrentHashMap<>();

    public void addTeam(ITeam team) {
        if (team == null) {
            return;
        }

        teamMap.put(team.getUid(), team);
    }

    public ITeam getTeam(Long uid) {
        return teamMap.get(uid);
    }
}