package com.wan37.logic.currency.encode;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.currency.database.CurrencyItemDb;
import com.wan37.logic.props.config.VirtualItemCfg;
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
    private ConfigLoader configLoader;

    public String encode(CurrencyItemDb itemDb) {
        Integer cfgId = itemDb.getId();

        String msg = "%s：%s（cfgId：%s）";
        return String.format(msg, configLoader.loadName(VirtualItemCfg.class, cfgId), itemDb.getAmount(), cfgId);
    }
}
