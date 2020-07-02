package com.zhengpp.padis.server.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * 服务端自定义业务处理handler
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 对每一个传入的消息都要调用；
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("server received: "+msg.toString());
        ctx.write(msg.toString());
    }


    /**
     * 通知ChannelInboundHandler最后一次对channelRead()的调用时当前批量读取中的的最后一条消息。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                //.addListener(ChannelFutureListener.CLOSE)
                ;
    }

    /**
     * 在读取操作期间，有异常抛出时会调用。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws  Exception{
        System.out.println("id:" + ctx.channel().id());
        System.out.println("localAddress:"+ ctx.channel().localAddress());
        System.out.println("remoteAddress:" + ctx.channel().remoteAddress());
        System.out.println(getIP(ctx));
        ChannelMap.addChannel(getIP(ctx),getChannel(ctx));
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //将channel在Map中移除
        ChannelMap.removeChannel(getIP(ctx));
    }

    /**
     * 获取客户端IP地址
     * @param ctx
     * @return
     */
    public static String getIP(ChannelHandlerContext ctx) {
        String ip = "";
        String address = ctx.channel().remoteAddress().toString();
        int colonAt = address.indexOf(":");
        ip = address.substring(1, colonAt);
        return ip;
    }

    /**
     * 获取channel
     * @param ctx
     * @return
     */
    public static Channel getChannel(ChannelHandlerContext ctx){
        return ctx.channel();
    }


}
