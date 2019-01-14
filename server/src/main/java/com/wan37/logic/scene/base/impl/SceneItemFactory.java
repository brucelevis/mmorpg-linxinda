package com.wan37.logic.scene.base.impl;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterItemCfg;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.scene.base.SceneItem;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdUtil;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class SceneItemFactory implements SceneItem.Factory {

    /**
     * FIXME: 写死虚物id小于200
     */
    private static final int MAX_VIRTUAL_ITEM_ID = 200;

    @Autowired
    private ConfigLoader configLoader;

    @Override
    public List<SceneItem> create(MonsterCfg monsterCfg) {
        //FIXME: 写死物品掉落1分钟过期
        long expireTime = DateTimeUtils.toEpochMilli(LocalDateTime.now()) + TimeUnit.MINUTES.toMillis(1);

        return monsterCfg.getItems().stream()
                .map(i -> createItem(i, expireTime))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private SceneItem createItem(MonsterItemCfg cfg, long expireTime) {
        if (RandomUtil.isNotHit(cfg.getProbability())) {
            return null;
        }

        Long uid = IdUtil.generate();
        if (cfg.getCfgId() < MAX_VIRTUAL_ITEM_ID) {
            return createVirtualItem(uid, cfg.getCfgId(), cfg.getAmount(), expireTime);
        }

        return createProps(uid, cfg.getCfgId(), cfg.getAmount(), expireTime);
    }

    private SceneItem createVirtualItem(Long uid, Integer id, int amount, long expireTime) {
        VirtualItemCfg cfg = configLoader.load(VirtualItemCfg.class, id).orElse(null);
        if (cfg == null) {
            return null;
        }

        return new VirtualItemImpl(uid, cfg, amount, expireTime);
    }

    private SceneItem createProps(Long uid, Integer id, int amount, long expireTime) {
        PropsCfg cfg = configLoader.load(PropsCfg.class, id).orElse(null);
        if (cfg == null) {
            return null;
        }

        return new ItemImpl(uid, cfg, amount, expireTime);
    }
}
