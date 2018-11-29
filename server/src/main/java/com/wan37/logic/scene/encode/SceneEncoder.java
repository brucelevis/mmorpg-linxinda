package com.wan37.logic.scene.encode;

import com.wan37.logic.faction.config.FactionCfgLoader;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.npc.Npc;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.scene.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Deprecated
@Service
public class SceneEncoder {

    @Autowired
    private FactionCfgLoader factionCfgLoader;

    @Autowired
    private MonsterEncoder monsterEncoder;

    @Autowired
    private SceneItemEncoder sceneItemEncoder;

    public String encode(Scene scene) {
        String sceneHead = String.format("当前场景：%s\n", scene.getSceneCfg().getName());

        String playerHead = "当前场景人物：\n";
        String players = scene.getPlayers().stream()
                .map(this::encodePlayer)
                .collect(Collectors.joining("\n"));

        String monsterHead = "当前场景怪物：\n";
        String monsters = scene.getMonsters().stream()
                .filter(Monster::isAlive)
                .map(m -> monsterEncoder.encode(m))
                .collect(Collectors.joining("\n"));

        String npcHead = "当前场景npc：\n";
        String npcs = scene.getNpcs().stream()
                .map(this::encodeNpc)
                .collect(Collectors.joining("\n"));

        String itemHead = "当前场景物品：\n";
        String items = scene.getItems().values().stream()
                .map(i -> sceneItemEncoder.encode(i))
                .collect(Collectors.joining("\n"));

        return sceneHead
                + playerHead + players + "\n"
                + monsterHead + monsters + "\n"
                + npcHead + npcs + "\n"
                + itemHead + items;
    }

    private String encodePlayer(Player player) {
        PlayerDb db = player.getPlayerDb();
        String factionName = factionCfgLoader.getFactionName(db.getFactionId());

        String msg = "名字：%s，职业：%s, 等级：%s, Hp：%s，Mp：%s，Exp: %s （playerUid：%s）";
        return String.format(msg, db.getName(), factionName, db.getLevel(), db.getHp(), db.getMp(), db.getExp(), db.getUid());
    }

    private String encodeNpc(Npc npc) {
        String msg = "名字：%s ";
        return String.format(msg, npc.getName());
    }
}
