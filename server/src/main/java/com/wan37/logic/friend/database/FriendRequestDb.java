package com.wan37.logic.friend.database;

import java.util.List;

public class FriendRequestDb {

    private List<FriendRequestInfoDb> requestList;

    public List<FriendRequestInfoDb> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<FriendRequestInfoDb> requestList) {
        this.requestList = requestList;
    }
}
