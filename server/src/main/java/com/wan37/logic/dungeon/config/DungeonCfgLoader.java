package com.wan37.logic.dungeon.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.DungeonCfgExcel;
import com.wan37.logic.dungeon.config.impl.DungeonCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DungeonCfgLoader implements GeneralCfgLoader<DungeonCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<DungeonCfg> loads() {
        return configManager.loads(DungeonCfgExcel.class).stream()
                .map(DungeonCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DungeonCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}
