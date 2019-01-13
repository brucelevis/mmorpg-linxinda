package com.wan37.logic.attr.encode;

import com.wan37.logic.attr.database.PlayerEachAttrDb;
import com.wan37.logic.attr.database.PlayerAttrDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 玩家属性信息编码
 *
 * @author linda
 */
@Service
public class PlayerAttrInfoEncoder {

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    public String encode(PlayerAttrDb playerAttrDb) {
        String head = "玩家基础属性：\n";
        String attrs = playerAttrDb.getAttrs().values().stream()
                .map(this::encodeAttr)
                .collect(Collectors.joining("，"));

        return head + attrs;
    }

    private String encodeAttr(PlayerEachAttrDb playerEachAttrDb) {
        String msg = "%s：%s";
        return String.format(msg, attrCfgLoader.getName(playerEachAttrDb.getCfgId()), playerEachAttrDb.getValue());
    }
}
