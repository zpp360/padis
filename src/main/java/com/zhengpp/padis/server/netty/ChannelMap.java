package com.zhengpp.padis.server.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zpp360 on 2019/9/8.
 */
public class ChannelMap {

    public static Map<String,Channel> map = new ConcurrentHashMap<>();

    public static Channel get(String ip){
        return map.get(ip);
    }

    public static Map put(String ip,Channel channel){
        map.put(ip,channel);
        return map;
    }

    public static void remove(String ip){
        map.remove(ip);
    }

    public static boolean containsIp(String ip){
        return map.containsKey(ip);
    }

    public static boolean containsChannel(Channel channel){
        return map.containsValue(channel);
    }

}
