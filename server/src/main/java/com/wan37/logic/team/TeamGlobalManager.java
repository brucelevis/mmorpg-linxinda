package com.wan37.logic.team;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组队全局管理器
 *
 * @author linda
 */
@Service
public class TeamGlobalManager {

    /**
     * key：teamUid
     */
    private static Map<Long, Team> teamMap = new ConcurrentHashMap<>();

    public void addTeam(Team team) {
        if (team == null) {
            return;
        }

        teamMap.put(team.getUid(), team);
    }

    public Team getTeam(Long uid) {
        return teamMap.get(uid);
    }

    public void rmTeam(Long uid) {
        teamMap.remove(uid);
    }
}