package com.wan37.logic.currency.encode;

import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.database.CurrencyItemDb;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Deprecated
@Service
public class CurrencyInfoEncoder {

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    public String encode(CurrencyDb currencyDb) {
        String head = "虚拟物品如下：\n";
        String content = currencyDb.getItemMap().values().stream()
                .map(this::encodeItem)
                .collect(Collectors.joining("\n"));

        return head + content;
    }

    private String encodeItem(CurrencyItemDb itemDb) {
        Integer cfgId = itemDb.getId();

        String msg = "名字：%s，数量：%s（cfgId：%s）";
        return String.format(msg, virtualItemCfgLoader.getName(cfgId), itemDb.getAmount(), cfgId);
    }
}
