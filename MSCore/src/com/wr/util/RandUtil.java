package com.wr.util;

import java.util.Random;

public class RandUtil
{
    private static Random GLOBAL_RANDOM = new Random(System.currentTimeMillis());

    public static int nextInt(int boundary)
    {
        return GLOBAL_RANDOM.nextInt(boundary);
    }

    public static double nextDouble()
    {
        return GLOBAL_RANDOM.nextDouble();
    }
}
