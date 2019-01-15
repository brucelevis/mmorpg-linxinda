package com.wan37.logic.pk.impl;

import com.wan37.logic.pk.Pk;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linda
 */
public class PkImpl implements Pk {

    public PkImpl(ReentrantLock lock, Map<Long, T> inviteMap) {
        this.lock = lock;
        this.inviteMap = inviteMap;
    }

    @Override
    public boolean isLock() {
        return lock.isLocked();
    }

    @Override
    public boolean tryLock() {
        return lock.tryLock();
    }

    @Override
    public void unLock() {
        if (isLock()) {
            lock.unlock();
        }
    }

    @Override
    public boolean isPking() {
        return isPking;
    }

    @Override
    public void setPking(boolean isPking) {
        this.isPking = isPking;
    }

    @Override
    public void addInviter(Long playerUid) {
        inviteMap.put(playerUid, null);
    }

    @Override
    public boolean hadInvited(Long playerUid) {
        return inviteMap.containsKey(playerUid);
    }

    @Override
    public void rmInviter(Long playerUid) {
        inviteMap.remove(playerUid);
    }

    private final ReentrantLock lock;
    private final Map<Long, T> inviteMap;

    private volatile boolean isPking;
}
