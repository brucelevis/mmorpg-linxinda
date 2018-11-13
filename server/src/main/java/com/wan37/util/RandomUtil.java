package com.wan37.util;

import java.util.Random;

public class RandomUtil {

    public static boolean isHit(double pro) {
        Random random = new Random();
        return pro - random.nextDouble() > 0;
    }
}
