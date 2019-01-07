package com.wan37.logic.mission.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.MissionCfgExcel;
import com.wan37.logic.mission.config.impl.MissionCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 任务配置表加载器
 *
 * @author linda
 */
@Service
public class MissionCfgLoader implements GeneralCfgLoader<MissionCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<MissionCfg> loads() {
        return configManager.loads(MissionCfgExcel.class).stream()
                .map(MissionCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MissionCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}
