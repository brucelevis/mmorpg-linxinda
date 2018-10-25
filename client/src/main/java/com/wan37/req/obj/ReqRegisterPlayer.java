package com.wan37.req.obj;

public class ReqRegisterPlayer {

    private final String req;
    private final long account;
    private final String password;

    public ReqRegisterPlayer(String req, long account, String password) {
        this.req = req;
        this.account = account;
        this.password = password;
    }

    public long getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getReq() {
        return req;
    }
}
