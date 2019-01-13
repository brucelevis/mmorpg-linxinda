package com.wan37.logic.currency.encode;

import com.wan37.logic.currency.database.CurrencyItemDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 每种虚物信息编码
 *
 * @author linda
 */
@Service
public class CurrencyItemEncoder {

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    public String encode(CurrencyItemDb itemDb) {
        Integer cfgId = itemDb.getId();

        String msg = "%s：%s（cfgId：%s）";
        return String.format(msg, virtualItemCfgLoader.getName(cfgId), itemDb.getAmount(), cfgId);
    }
}
