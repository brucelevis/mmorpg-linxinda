package com.wan37.logic.buff.effect.behavior.behaviors;

import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.monster.die.MonsterDieHandler;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.SceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓慢扣血
 */
@Service
class BuffEffectBehav2 implements BuffEffectBehavior {

    @Autowired
    private MonsterDieHandler monsterDieHandler;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private MonsterEncoder monsterEncoder;

    @Override
    public void behave(BuffEffectContext context) {
        FightingUnit unit = context.getUnit();
        IBuff buff = context.getBuff();
        long now = context.getNow();

        //FIXME: 待解决人与怪如何通用处理
//        if (monster != null) {
//            long lastAttackId = monster.getLastAttackId();
//            Player player = playerGlobalManager.getPlayerByUid(lastAttackId);
//            Scene scene = sceneGlobalManager.getScene(player.getSceneId());

        long curHp = unit.getHp();
        int subHp = Integer.parseInt(buff.getArg());
        if (curHp > subHp) {
            unit.setHp(curHp - subHp);

            long lastEffectTime = buff.getLastEffectTime();
            if (lastEffectTime == 0) {
                // 第一次生效
                buff.setLastEffectTime(buff.getExpireTime() - buff.getContinuous());
            } else {
                buff.setLastEffectTime(lastEffectTime + buff.getInterval());
            }
        } else {
            // 死了
            //  monsterDieHandler.handle(monster, now, player, scene);
        }

        // 通知场景玩家怪物状态更新
//        String monsterUpdate = "怪物状态更新推送|" + monsterEncoder.encode(monster);
//        scene.getPlayers().forEach(p -> p.syncClient(monsterUpdate));
    }
}
