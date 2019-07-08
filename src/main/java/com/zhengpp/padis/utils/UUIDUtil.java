package com.zhengpp.padis.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by zpp360 on 2019/7/6.
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

    public static void main(String[] args){
        System.out.println(UUIDUtil.uuid());
    }
}
