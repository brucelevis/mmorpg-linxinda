package com.wan37.logic.monster.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.entity.MonsterCfgExcel;
import com.wan37.logic.monster.config.impl.MonsterCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonsterCfgLoader {

    @Autowired
    private ConfigManager configManager;

    public List<MonsterCfg> loads() {
        return configManager.loads(MonsterCfgExcel.class).stream()
                .map(MonsterCfgImpl::new)
                .collect(Collectors.toList());
    }

    public Optional<MonsterCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}