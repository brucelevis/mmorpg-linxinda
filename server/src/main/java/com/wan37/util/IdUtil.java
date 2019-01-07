package com.wan37.util;

import java.util.UUID;

/**
 * id生成类
 *
 * @author linda
 */
public class IdUtil {

    public static Long generate() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }
}
