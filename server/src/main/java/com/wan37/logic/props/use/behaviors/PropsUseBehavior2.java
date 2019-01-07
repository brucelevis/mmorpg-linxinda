package com.wan37.logic.props.use.behaviors;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PlayerEachAttrDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.props.use.PropsUseBehavior;
import com.wan37.logic.props.use.PropsUseContext;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.stereotype.Service;

/**
 * 一次性回复血量
 */
@Service
class PropsUseBehavior2 implements PropsUseBehavior {

    @Override
    public void behave(PropsUseContext context) {
        Player player = context.getPlayer();
        PropsCfg propsCfg = context.getPropsCfg();
        int addHp = Integer.parseInt(propsCfg.getUseLogicArgs());

        PlayerDb playerDb = player.getPlayerDb();
        long cur = playerDb.getHp();
        long hp = cur + addHp;

        PlayerEachAttrDb hpDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_HP.getId());
        if (hpDb == null) {
            return;
        }

        long max = Math.round(hpDb.getValue());
        long result = max > hp ? hp : max;
        if (result == cur) {
            return;
        }

        playerDb.setHp(result);

        String msg = String.format("你恢复了%shp,", result - cur);
        player.syncClient(msg);

        // TODO: 推送玩家状态变化给场景其他玩家
    }
}
