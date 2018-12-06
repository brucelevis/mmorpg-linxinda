package com.wan37.logic.player.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.ExpCfgExcel;
import com.wan37.logic.player.config.impl.ExpCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpCfgLoader implements GeneralCfgLoader<ExpCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<ExpCfg> loads() {
        return configManager.loads(ExpCfgExcel.class).stream()
                .map(ExpCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ExpCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}
