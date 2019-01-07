package com.wan37.logic.summoning.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.SummoningCfgExcel;
import com.wan37.logic.summoning.config.impl.SummoningCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 召唤兽配置表加载器
 *
 * @author linda
 */
@Service
public class SummoningCfgLoader implements GeneralCfgLoader<SummoningCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<SummoningCfg> loads() {
        return configManager.loads(SummoningCfgExcel.class).stream()
                .map(SummoningCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SummoningCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}
