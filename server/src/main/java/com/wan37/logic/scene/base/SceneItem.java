package com.wan37.logic.scene.base;

import com.wan37.logic.monster.config.MonsterCfg;

import java.util.List;

/**
 * 场景物品
 *
 * @author linda
 */
public interface SceneItem {

    interface Factory {

        /**
         * 怪物掉落配置生成场景物品
         *
         * @param monsterCfg 怪物配置表
         * @return List<SceneItem>
         */
        List<SceneItem> create(MonsterCfg monsterCfg);

        //TODO: 工厂支持生成玩家丢出来的背包物品
    }

    /**
     * 场景物品唯一id
     *
     * @return Long
     */
    Long getUid();

    /**
     * 物品（实物，虚物）id
     *
     * @return Integer
     */
    Integer getCfgId();

    /**
     * 数量
     *
     * @return int
     */
    int getAmount();

    /**
     * 物品名
     *
     * @return String
     */
    String getName();

    /**
     * 过期时间
     *
     * @return long
     */
    long getExpireTime();
}
