package com.firstopiniondentist.util;

import java.util.UUID;

/**
 * Created by jasmeetsingh on 2/20/16.
 */
public class GeneralUtil {

    public static String getId(){
        return UUID.randomUUID().toString();
    }
}
