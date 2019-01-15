package com.wan37.logic.pk.service;

import com.wan37.logic.pk.Pk;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class PkInviteExec {

    public void exec(Player player, Player target) {
        Pk targetPk = target.getPk();
        if (targetPk.isPking()) {
            player.syncClient("对方正在决斗");
            return;
        }

        if (targetPk.hadInvited(player.getUid())) {
            player.syncClient("你已经邀请过了，不可重复邀请");
            return;
        }

        targetPk.addInviter(player.getUid());
        player.syncClient(String.format("你发起了对[%s]的决斗邀请", target.getName()));
        target.syncClient(String.format("[%s]向你发起了决斗邀请（playerUid：%s）", player.getName(), player.getUid()));
    }
}
