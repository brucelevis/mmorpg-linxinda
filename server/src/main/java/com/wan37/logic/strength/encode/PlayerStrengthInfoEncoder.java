package com.wan37.logic.strength.encode;

import com.wan37.logic.strength.database.PlayerStrengthDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 玩家战力信息编码
 *
 * @author linda
 */
@Service
public class PlayerStrengthInfoEncoder {

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    public String encode(PlayerStrengthDb playerStrengthDb) {
        String head = String.format("玩家面板总伤：%s，总防：%s\n", playerStrengthDb.getBaseAttackVal(), playerStrengthDb.getBaseDefenseVal());

        String attrs = playerStrengthDb.getAttrs().entrySet().stream()
                .map(e -> encodeAttr(e.getKey(), e.getValue()))
                .collect(Collectors.joining("，"));

        return head + attrs;
    }

    private String encodeAttr(Integer id, double value) {
        String msg = "%s：%s";
        return String.format(msg, attrCfgLoader.getName(id), value);
    }
}
