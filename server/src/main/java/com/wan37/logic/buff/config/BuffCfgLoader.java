package com.wan37.logic.buff.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.BuffCfgExcel;
import com.wan37.logic.buff.config.impl.BuffCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuffCfgLoader implements GeneralCfgLoader<BuffCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<BuffCfg> loads() {
        return configManager.loads(BuffCfgExcel.class).stream()
                .map(BuffCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BuffCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }

    public String getName(Integer id) {
        return load(id)
                .map(BuffCfg::getName)
                .orElse("NULL");
    }
}
