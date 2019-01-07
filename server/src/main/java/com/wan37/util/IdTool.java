package com.wan37.util;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * id生成类
 */
@Service
public class IdTool {

    public Long generate() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }
}
