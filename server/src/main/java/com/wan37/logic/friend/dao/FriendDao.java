package com.wan37.logic.friend.dao;

import com.wan37.logic.friend.database.FriendDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

/**
 * 好友系统数据库操作
 *
 * @author linda
 */
@Service
public interface FriendDao extends Repository<FriendDb, Long> {

    /**
     * 保存好友系统数据
     *
     * @param friendDb 玩家好友系统数据
     */
    void save(FriendDb friendDb);
}
