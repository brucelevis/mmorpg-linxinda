package com.wan37.logic.guild.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.GuildPositionCfgExcel;
import com.wan37.logic.guild.config.impl.GuildPositionCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 公会职位配置表加载器
 *
 * @author linda
 */
@Service
public class GuildPositionCfgLoader implements GeneralCfgLoader<GuildPositionCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<GuildPositionCfg> loads() {
        return configManager.loads(GuildPositionCfgExcel.class).stream()
                .map(GuildPositionCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GuildPositionCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}
