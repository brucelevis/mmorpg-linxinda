package com.wan37.logic.player.dao;

import com.wan37.logic.player.database.PlayerDb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

/**
 * 玩家数据库操作
 *
 * @author linda
 */
@Service
public interface PlayerDao extends Repository<PlayerDb, Long> {

    /**
     * 指定玩家唯一id返回玩家数据库实例
     *
     * @param uid 玩家唯一id
     * @return PlayerDb
     */
    PlayerDb getByUid(Long uid);

    /**
     * 保存玩家数据
     *
     * @param playerDb 玩家数据库实例
     */
    void save(PlayerDb playerDb);

    /**
     * 玩家是否存在
     *
     * @param uid 玩家唯一id
     * @return boolean
     */
    boolean existsByUid(Long uid);

    /**
     * 通过玩家uid查询玩家名
     *
     * @param uid 玩家唯一id
     * @return String
     */
    @Query(value = "select p.name from PlayerDb p where p.uid = ?1")
    String find(Long uid);
}
