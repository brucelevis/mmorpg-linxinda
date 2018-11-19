package com.wan37.logic.player.service;

import com.wan37.logic.buff.IBuff;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerBuffAdder {

    public void add(Player player, IBuff buff) {
        // 去重
        List<IBuff> buffs = player.getBuffs();
        buffs.stream().filter(b -> com.google.common.base.Objects.equal(b.getId(), buff.getId()))
                .findAny()
                .ifPresent(buffs::remove);

        buffs.add(buff);
    }
}
