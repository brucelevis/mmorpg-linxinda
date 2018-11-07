package com.wan37.logic.backpack.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.encode.BackpackUpdateNotifyEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackpackUpdateNotifier {

    @Autowired
    private BackpackUpdateNotifyEncoder backpackUpdateNotifyEncoder;

    public void notify(Player player) {
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
