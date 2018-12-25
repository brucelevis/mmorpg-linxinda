package com.wan37.logic.pk.entity;

public interface IPk {

    boolean isLock();

    boolean tryLock();

    void unLock();

    boolean isPking();

    void setPking(boolean isPking);

    void addInviter(Long playerUid);

    boolean hadInvited(Long playerUid);

    void rmInviter(Long playerUid);
}
