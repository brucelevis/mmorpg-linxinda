package com.wan37.logic.scene.encode;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.scene.base.SceneActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author linda
 */
@Service
public class SceneActorEncoder {

    @Autowired
    private MonsterEncoder monsterEncoder;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    @Deprecated
    public String encode(SceneActor sceneActor) {
        if (sceneActor instanceof Player) {
            Player player = (Player) sceneActor;
            return "人物状态更新|" + playerInfoEncoder.encode(player);
        }

        if (sceneActor instanceof Monster) {
            Monster monster = (Monster) sceneActor;
            return "场景怪物状态更新|" + monsterEncoder.encode(monster);
        }

        return "";
    }
}
