package com.wan37.logic.attr.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.AttrCfgExcel;
import com.wan37.logic.attr.config.impl.AttrCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttrCfgLoader implements GeneralCfgLoader<AttrCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<AttrCfg> loads() {
        return configManager.loads(AttrCfgExcel.class).stream()
                .map(AttrCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AttrCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }

    public String getName(Integer id) {
        return load(id)
                .map(AttrCfg::getName)
                .orElse("");
    }
}
