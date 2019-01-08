package com.wan37.logic.scene.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.entity.SceneCfgExcel;
import com.wan37.logic.scene.config.impl.SceneCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 场景配置表加载器
 *
 * @author linda
 */
@Service
public class SceneCfgLoader {

    @Autowired
    private ConfigManager configManager;

    public List<SceneCfg> loads() {
        return configManager.loads(SceneCfgExcel.class).stream()
                .map(SceneCfgImpl::new)
                .collect(Collectors.toList());
    }

    public Optional<SceneCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }

    public Optional<SceneCfg> loadDefault() {
        return loads().stream()
                .filter(SceneCfg::isDefault)
                .findAny();
    }

    public String loadName(Integer id) {
        return load(id)
                .map(SceneCfg::getName)
                .orElse("NULL");
    }
}
