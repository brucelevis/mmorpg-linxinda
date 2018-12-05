package com.wan37.logic.friend.database.convert;

import com.wan37.Utils.JsonUtil;
import com.wan37.logic.friend.database.FriendRequestDb;

import javax.persistence.AttributeConverter;

public class FriendRequestDbConverterImpl implements AttributeConverter<FriendRequestDb, String> {

    @Override
    public String convertToDatabaseColumn(FriendRequestDb friendRequestDb) {
        return JsonUtil.parseJson(friendRequestDb);
    }

    @Override
    public FriendRequestDb convertToEntityAttribute(String s) {
        return (FriendRequestDb) JsonUtil.parseObj(s, FriendRequestDb.class);
    }
}
