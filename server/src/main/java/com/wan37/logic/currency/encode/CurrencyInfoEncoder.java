package com.wan37.logic.currency.encode;

import com.wan37.logic.currency.database.CurrencyDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 玩家虚物信息编码
 *
 * @author linda
 */
@Service
public class CurrencyInfoEncoder {

    @Autowired
    private CurrencyItemEncoder currencyItemEncoder;

    public String encode(CurrencyDb currencyDb) {
        String head = "虚拟物品如下：\n";
        String content = currencyDb.getItemMap().values().stream()
                .map(i -> currencyItemEncoder.encode(i))
                .collect(Collectors.joining("\n"));

        return head + content;
    }
}
