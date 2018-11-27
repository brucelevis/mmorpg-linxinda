package com.wan37.util;

import java.util.Random;

public class RandomUtil {

    public static boolean isHit(double pro) {
        Random random = new Random();
        return pro - random.nextDouble() > 0;
    }

    public static int rand(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
}
