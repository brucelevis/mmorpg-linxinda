package com.wan37.logic.friend.dao;

import com.wan37.logic.friend.database.FriendRequestDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public interface FriendRequestDao extends Repository<FriendRequestDb, Long> {

    void save(FriendRequestDb friendRequestDb);
}
