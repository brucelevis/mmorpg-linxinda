package com.wan37.logic.skill.cast.check;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.EquipPartEnum;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.entity.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家释放技能前独有的检查
 */
@Service
public class PlayerSkillBeforeCastChecker {

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    public void check(Player player, ISkill skill) {
        PlayerDb playerDb = player.getPlayerDb();
        EquipDb equipDb = playerDb.getEquipDb();
        ItemDb equipItem = equipDb.getItems().get(EquipPartEnum.PART_1.getId());
        if (equipItem == null) {
            throw new GeneralErrorException("未佩戴武器，无法施放技能");
        }

        EquipExtraDb equipExtraDb = equipExtraDbGetter.get(equipItem.getExtraDb());
        if (equipExtraDb.getDurability() < 20) {
            //FIXME: 写死攻击时武器耐久度要求
            throw new GeneralErrorException("武器耐久度过低，请及时修理");
        }

        // 检查蓝量
        int costMp = skill.getCostMp();
        if (player.getMp() < costMp) {
            throw new GeneralErrorException("蓝量不足，无法施放技能");
        }
    }
}
