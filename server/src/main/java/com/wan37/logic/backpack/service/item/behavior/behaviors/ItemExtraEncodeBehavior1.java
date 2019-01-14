package com.wan37.logic.backpack.service.item.behavior.behaviors;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavior;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehaviorContext;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipQualityCfg;
import com.wan37.logic.equipment.database.EquipAttrDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * 1：装备
 */
@Service
class ItemExtraEncodeBehavior1 implements ItemExtraEncodeBehavior {

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    @Autowired
    private ConfigLoader configLoader;

    @Override
    public void behave(ItemExtraEncodeBehaviorContext context) {
        EquipExtraDb equipExtraDb = equipExtraDbGetter.get(context.getExtraDb());

        String durability = String.format("耐久度：%s，", equipExtraDb.getDurability());
        String msg = String.format("装备属性信息（品级：%s）：", encodeQuality(context.getCfgId(), equipExtraDb)) + encodeExtra(equipExtraDb);
        context.setResult(durability + msg);
    }

    private String encodeExtra(EquipExtraDb db) {
        return db.getBaseAttrs().stream()
                .map(this::encodeAttr)
                .collect(Collectors.joining("，"));
    }

    private String encodeQuality(Integer cfgId, EquipExtraDb db) {
        int totalScore = 0;
        for (EquipAttrDb attrDb : db.getBaseAttrs()) {
            AttrCfg attrCfg = configLoader.load(AttrCfg.class, attrDb.getCfgId())
                    .orElseThrow(() -> new RuntimeException("找不到对应的属性配置表"));

            totalScore += attrCfg.getEquipBaseScore() * attrDb.getValue();
        }

        EquipCfg equipCfg = configLoader.load(EquipCfg.class, cfgId)
                .orElseThrow(() -> new RuntimeException("找不到对应的装备配置表"));

        int finalTotalScore = totalScore;
        return equipCfg.getQuality().stream()
                .filter(c -> finalTotalScore >= c.getScore())
                .max(Comparator.comparingInt(EquipQualityCfg::getScore))
                .map(EquipQualityCfg::getName)
                .orElseThrow(() -> new RuntimeException("装备配置表找不到对应的品级"));
    }

    private String encodeAttr(EquipAttrDb db) {
        String msg = "%s：%s";
        return String.format(msg, configLoader.loadName(AttrCfg.class, db.getCfgId()), db.getValue());
    }
}
