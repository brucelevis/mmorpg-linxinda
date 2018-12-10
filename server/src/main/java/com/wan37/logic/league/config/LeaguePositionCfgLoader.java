package com.wan37.logic.league.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.LeaguePositionCfgExcel;
import com.wan37.logic.league.config.impl.LeaguePositionCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaguePositionCfgLoader implements GeneralCfgLoader<LeaguePositionCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<LeaguePositionCfg> loads() {
        return configManager.loads(LeaguePositionCfgExcel.class).stream()
                .map(LeaguePositionCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LeaguePositionCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}
