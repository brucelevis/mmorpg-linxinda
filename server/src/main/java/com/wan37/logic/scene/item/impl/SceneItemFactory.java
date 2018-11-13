package com.wan37.logic.scene.item.impl;

import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterItemCfg;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import com.wan37.logic.scene.item.SceneItem;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdTool;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SceneItemFactory implements SceneItem.Factory {

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private IdTool idTool;

    @Override
    public List<SceneItem> create(MonsterCfg monsterCfg) {
        //FIXME: 写死物品掉落1分钟过期
        long expireTime = DateTimeUtils.toEpochMilli(LocalDateTime.now()) + TimeUnit.MINUTES.toMinutes(1);

        return monsterCfg.getItems().stream()
                .map(i -> createItem(i, expireTime))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private SceneItem createItem(MonsterItemCfg cfg, long expireTime) {
        if (!RandomUtil.isHit(cfg.getProbability())) {
            return null;
        }

        Long uid = idTool.generate();
        if (cfg.getCfgId() < 200) {
            return createVirtualItem(uid, cfg.getCfgId(), cfg.getAmount(), expireTime);
        }

        return createProps(uid, cfg.getCfgId(), cfg.getAmount(), expireTime);
    }

    private SceneItem createVirtualItem(Long uid, Integer id, int amount, long expireTime) {
        VirtualItemCfg cfg = virtualItemCfgLoader.load(id).orElse(null);
        if (cfg == null) {
            return null;
        }

        return new VirtualItemImpl(uid, cfg, amount, expireTime);
    }

    private SceneItem createProps(Long uid, Integer id, int amount, long expireTime) {
        PropsCfg cfg = propsCfgLoader.load(id).orElse(null);
        if (cfg == null) {
            return null;
        }

        return new ItemImpl(uid, cfg, amount, expireTime);
    }
}
