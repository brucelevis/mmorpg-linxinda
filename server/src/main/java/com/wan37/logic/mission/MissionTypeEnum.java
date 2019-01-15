package com.wan37.logic.mission;

/**
 * 任务类型枚举
 *
 * @author linda
 */
public enum MissionTypeEnum {

    /**
     * 击杀怪物
     */
    KILL_MONSTER(1),

    /**
     * 提升等级
     */
    LEVEL_UP(2),

    /**
     * npc对话
     */
    NPC_TALK(3),

    /**
     * 某种虚物达到多少
     */
    VIRTUAL_ITEM_AMOUNT(4),

    /**
     * 添加一个好友
     */
    ADD_FRIEND(5),

    /**
     * 装备穿戴等级总和达到多少
     */
    TOTAL_EQUIP_LEVEL(6),

    /**
     * 第一次加入组队
     */
    FIRST_JOIN_TEAM(7),

    /**
     * 第一次加入公会
     */
    FIRST_JOIN_GUILD(8),

    /**
     * 第一次与玩家交易成功
     */
    FIRST_SUCCESS_TRADE(9),

    /**
     * 完成系列任务
     */
    COMPLETE_SERIES_MISSION(10),

    /**
     * 通关某个副本n次
     */
    PASS_DUNGEON(11),

    /**
     * 获得n件达到最高品级的装备
     */
    GET_PERFECT_EQUIP(12),

    /**
     * 第一次在pk中战胜
     */
    FIRST_PK_WIN(13);

    private Integer id;

    MissionTypeEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
