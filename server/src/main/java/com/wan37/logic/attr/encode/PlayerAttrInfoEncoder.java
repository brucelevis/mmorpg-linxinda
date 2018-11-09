package com.wan37.logic.attr.encode;

import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.attr.database.PlayerAttrDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Deprecated
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

    private String encodeAttr(PAttrDb pAttrDb) {
        String msg = "%s：%s";
        return String.format(msg, attrCfgLoader.getName(pAttrDb.getCfgId()), pAttrDb.getValue());
    }
}
