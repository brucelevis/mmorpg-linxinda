package com.wan37.logic.skill.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.SkillCfgExcel;
import com.wan37.logic.skill.config.impl.SkillCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 技能配置表加载器
 *
 * @author linda
 */
@Service
public class SkillCfgLoader implements GeneralCfgLoader<SkillCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<SkillCfg> loads() {
        return configManager.loads(SkillCfgExcel.class).stream()
                .map(SkillCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SkillCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }

    public String getName(Integer id) {
        return load(id)
                .map(SkillCfg::getName)
                .orElse("");
    }
}
