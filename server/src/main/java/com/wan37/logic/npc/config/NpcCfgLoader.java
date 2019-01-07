package com.wan37.logic.npc.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.entity.NpcCfgExcel;
import com.wan37.logic.npc.config.impl.NpcCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Npc配置表加载器
 *
 * @author linda
 */
@Service
public class NpcCfgLoader {

    @Autowired
    private ConfigManager configManager;

    public List<NpcCfg> loads() {
        return configManager.loads(NpcCfgExcel.class).stream()
                .map(NpcCfgImpl::new)
                .collect(Collectors.toList());
    }

    public Optional<NpcCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }

    public String loadName(Integer id) {
        return load(id)
                .map(NpcCfg::getName)
                .orElse("NULL");
    }
}
