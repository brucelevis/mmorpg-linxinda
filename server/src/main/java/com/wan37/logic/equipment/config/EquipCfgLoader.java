package com.wan37.logic.equipment.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.EquipCfgExcel;
import com.wan37.logic.equipment.config.impl.EquipCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 装备配置加载器
 *
 * @author linda
 */
@Service
public class EquipCfgLoader implements GeneralCfgLoader<EquipCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<EquipCfg> loads() {
        return configManager.loads(EquipCfgExcel.class).stream()
                .map(EquipCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EquipCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}
