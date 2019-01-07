package com.wan37.logic.props.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.PropsCfgExcel;
import com.wan37.logic.props.config.impl.PropsCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 实物配置表加载器
 *
 * @author linda
 */
@Service
public class PropsCfgLoader implements GeneralCfgLoader<PropsCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<PropsCfg> loads() {
        return configManager.loads(PropsCfgExcel.class).stream()
                .map(PropsCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PropsCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }

    public String getName(Integer id) {
        return load(id)
                .map(PropsCfg::getName)
                .orElse("NULL");
    }
}
