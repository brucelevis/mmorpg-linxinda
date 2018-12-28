package com.wan37.logic.scene.encode;

import com.wan37.logic.scene.base.SceneItem;
import org.springframework.stereotype.Service;

@Service
public class SceneItemEncoder {

    public String encode(SceneItem item) {
        return String.format("%s：%s（uid：%s）", item.getName(), item.getAmount(), item.getUid());
    }
}
