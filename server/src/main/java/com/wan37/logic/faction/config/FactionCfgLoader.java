package com.wan37.logic.faction.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.FactionCfgExcel;
import com.wan37.logic.faction.config.impl.FactionCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 门派配置加载器
 *
 * @author linda
 */
@Service
public class FactionCfgLoader implements GeneralCfgLoader<FactionCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<FactionCfg> loads() {
        return configManager.loads(FactionCfgExcel.class).stream()
                .map(FactionCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FactionCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }

    public String getFactionName(Integer id) {
        return load(id)
                .map(FactionCfg::getName)
                .orElse("");
    }
}
