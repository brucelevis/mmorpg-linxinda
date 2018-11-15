package com.wan37.logic.props.behavior.use.behaviors;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.props.behavior.use.PropsUseBehavior;
import com.wan37.logic.props.behavior.use.PropsUseContext;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 一次性回复血量
 */
@Service
class PropsUseBehav2 implements PropsUseBehavior {

    @Autowired
    private PlayerDao playerDao;

    @Override
    public void behave(PropsUseContext context) {
        Player player = context.getPlayer();
        PropsCfg propsCfg = context.getPropsCfg();
        int addHp = Integer.parseInt(propsCfg.getUseLogicArgs());

        PlayerDb playerDb = player.getPlayerDb();
        int cur = playerDb.getHp();
        int hp = cur + addHp;

        PAttrDb hpDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_HP.getId());
        if (hpDb == null) {
            return;
        }

        int max = (int) Math.round(hpDb.getValue());
        int result = max > hp ? hp : max;
        if (result == cur) {
            return;
        }

        playerDb.setHp(result);
        playerDao.save(playerDb);

        String msg = String.format("你恢复了%shp,", result - cur);
        player.syncClient(msg);

        // TODO: 推送玩家状态变化给场景其他玩家
    }
}
