package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipCfgLoader;
import com.wan37.logic.equipment.config.EquipQualityCfg;
import com.wan37.logic.equipment.database.EquipAttrDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_12
 */
@Service
class MissionCompleteCheckBehav12 implements MissionCompleteCheckBehavior {

    @Autowired
    private EquipCfgLoader equipCfgLoader;

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    @Override
    public void behave(MissionCompleteCheckContext context) {
        Player player = context.getPlayer();
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();

        // 背包装备最高品质的数量
        int maxQualityEquipCount = (int) backpackDb.getItemMap().values().stream()
                .filter(i -> isEquip(i.getCfgId()))
                .filter(this::isMaxQuality)
                .count();

        MissionCfg missionCfg = context.getPlayerMission().getMissionCfg();
        if (maxQualityEquipCount < missionCfg.getArgsAsInt()) {
            return;
        }

        context.setResult(true);
    }

    private boolean isEquip(Integer cfgId) {
        return equipCfgLoader.load(cfgId).isPresent();
    }

    private boolean isMaxQuality(ItemDb itemDb) {
        int totalScore = 0;
        EquipExtraDb equipExtraDb = equipExtraDbGetter.get(itemDb.getExtraDb());
        for (EquipAttrDb attrDb : equipExtraDb.getBaseAttrs()) {
            AttrCfg attrCfg = attrCfgLoader.load(attrDb.getCfgId())
                    .orElseThrow(() -> new RuntimeException("找不到对应的属性配置表"));

            totalScore += attrCfg.getEquipBaseScore() * attrDb.getValue();
        }

        EquipCfg equipCfg = equipCfgLoader.load(itemDb.getCfgId()).orElse(null);
        assert equipCfg != null;

        // 装备配置最大品质的基准分
        int maxQualityScore = equipCfg.getQuality().stream()
                .max(Comparator.comparingInt(EquipQualityCfg::getScore))
                .map(EquipQualityCfg::getScore)
                .orElse(0);

        return totalScore >= maxQualityScore;
    }
}