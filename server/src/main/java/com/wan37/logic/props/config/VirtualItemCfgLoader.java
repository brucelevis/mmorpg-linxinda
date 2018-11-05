package com.wan37.logic.props.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.VirtualItemCfgExcel;
import com.wan37.logic.props.config.impl.VirtualItemCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VirtualItemCfgLoader implements GeneralCfgLoader<VirtualItemCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<VirtualItemCfg> loads() {
        return configManager.loads(VirtualItemCfgExcel.class).stream()
                .map(VirtualItemCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VirtualItemCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}
