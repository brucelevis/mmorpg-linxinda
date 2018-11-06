package com.wan37.logic.props;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.props.encode.BackpackUpdateNotifyEncoder;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.add.ResourceAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceFacade {

    @Autowired
    private ResourceAdder resourceAdder;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private BackpackUpdateNotifyEncoder backpackUpdateNotifyEncoder;

    public void giveResource(ResourceCollection res, Player player) {
        res.getElements().forEach(e -> resourceAdder.add(e, player));
        playerDao.save(player.getPlayerDb());

        // 背包更新推送
        backpackUpdateNotify(player);
    }

    private void backpackUpdateNotify(Player player) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        GeneralResponseDto dto = backpackUpdateNotifyEncoder.encode(ResultCode.BACKPACK_UPDATE, backpackDb);
        if (dto == null) {
            return;
        }

        player.syncClient(dto);

        // 背包格子变化标记清空
        backpackDb.getIndexs().clear();
    }
}
