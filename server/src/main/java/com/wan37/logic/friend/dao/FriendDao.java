package com.wan37.logic.friend.dao;

import com.wan37.logic.friend.database.FriendDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public interface FriendDao extends Repository<FriendDb, Long> {

    void save(FriendDb friendDb);

    FriendDb getByPlayerUid(Long uid);
}
