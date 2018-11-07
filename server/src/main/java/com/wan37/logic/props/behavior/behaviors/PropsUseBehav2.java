package com.wan37.logic.props.behavior.behaviors;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.encode.PlayerUpdateNotifyEncoder;
import com.wan37.logic.props.behavior.PropsUseBehavior;
import com.wan37.logic.props.behavior.PropsUseContext;
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

    @Autowired
    private PlayerUpdateNotifyEncoder playerUpdateNotifyEncoder;

    @Override
    public void behave(PropsUseContext context) {
        Player player = context.getPlayer();
        PropsCfg propsCfg = context.getPropsCfg();
        double addHp = Double.parseDouble(propsCfg.getUseLogicArgs());

        PlayerDb playerDb = player.getPlayerDb();
        double hp = playerDb.getHp() + addHp;

        PAttrDb hpDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_HP.getId());
        if (hpDb == null) {
            return;
        }

        double max = hpDb.getValue();
        double result = max > hp ? hp : max;
        playerDb.setHp(result);

        playerDao.save(playerDb);

        GeneralResponseDto dto = playerUpdateNotifyEncoder.encode(ResultCode.PLAYER_UPDATE, playerDb);
        player.syncClient(dto);

        // TODO: 推送玩家状态变化给场景其他玩家
    }
}
