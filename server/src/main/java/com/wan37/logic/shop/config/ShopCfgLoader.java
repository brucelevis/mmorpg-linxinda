package com.wan37.logic.shop.config;

import com.wan37.config.ConfigManager;
import com.wan37.config.GeneralCfgLoader;
import com.wan37.config.entity.ShopCfgExcel;
import com.wan37.logic.shop.config.impl.ShopCfgImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopCfgLoader implements GeneralCfgLoader<ShopCfg> {

    @Autowired
    private ConfigManager configManager;

    @Override
    public List<ShopCfg> loads() {
        return configManager.loads(ShopCfgExcel.class).stream()
                .map(ShopCfgImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShopCfg> load(Integer id) {
        return loads().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findAny();
    }
}