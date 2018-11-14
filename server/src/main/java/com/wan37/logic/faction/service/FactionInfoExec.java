package com.wan37.logic.faction.service;

import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.faction.config.FactionCfgLoader;
import com.wan37.util.GeneralNotifySenderUtil;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class FactionInfoExec {

    @Autowired
    private FactionCfgLoader factionCfgLoader;

    @Deprecated
    public void exec(Channel channel) {
        String head = "门派信息如下：\n";
        String msg = factionCfgLoader.loads().stream()
                .map(this::encode)
                .collect(Collectors.joining("\n"));

        GeneralNotifySenderUtil.send(channel, head + msg);
    }

    private String encode(FactionCfg factionCfg) {
        return String.format("%s（id：%s）", factionCfg.getName(), factionCfg.getId());
    }
}