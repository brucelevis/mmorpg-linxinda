package com.wan37.logic.pk.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.pk.entity.IPk;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

@Service
public class PkInviteExec {

    public void exec(Player player, Player target) {
        IPk targetPk = target.getPk();
        if (targetPk.isPking()) {
            throw new GeneralErrorException("对方正在决斗");
        }

        if (targetPk.hadInvited(player.getUid())) {
            throw new GeneralErrorException("你已经邀请过了，不可重复邀请");
        }

        targetPk.addInviter(player.getUid());
        player.syncClient(String.format("你发起了对[%s]的决斗邀请", target.getName()));
        target.syncClient(String.format("[%s]向你发起了决斗邀请（playerUid：%s）", player.getName(), player.getUid()));
    }
}
