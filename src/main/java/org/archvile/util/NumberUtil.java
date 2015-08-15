package org.archvile.util;

import java.util.Random;

public class NumberUtil {

    private static Random rand = new Random();

    public static int getRandomIntFromZeroTo(int max) {
        return getRandomIntBetweenRange(0, max);
    }

    /**
     * Get a random integer between a specified range (inclusive)
     * @param min
     * @param max
     * @return
     */
    public static int getRandomIntBetweenRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

}
