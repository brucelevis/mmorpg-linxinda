package com.wan37.util;

import java.util.Random;

/**
 * 随机工具类
 *
 * @author linda
 */
public class RandomUtil {

    @Deprecated
    public static boolean isHit(double probability) {
        Random random = new Random();
        return probability - random.nextDouble() > 0;
    }

    public static boolean isNotHit(double probability) {
        Random random = new Random();
        return random.nextDouble() - probability > 0;
    }

    public static int rand(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
}
