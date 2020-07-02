package com.zhengpp.padis.controller;

import com.zhengpp.padis.base.BaseController;
import com.zhengpp.padis.server.netty.ChannelMap;
import com.zhengpp.padis.utils.Constants;
import io.netty.channel.Channel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 手动模式控制
 */
@Controller
@RequestMapping("/manual")
public class ManualController extends BaseController {

    @RequestMapping("/config")
    @ResponseBody
    public String config(){
        Map map = new HashMap();
        map.put("temperature_gap", Constants.temperature_gap);
        map.put("localhost",ChannelMap.findChannel("192.168.0.119"));
        Channel channel =  ChannelMap.findChannel("192.168.0.119");
        channel.writeAndFlush("hello,server主动给你发消息啦");
        //ChannelMap.sendToAll("hello i am server");
        return "";
    }

}
