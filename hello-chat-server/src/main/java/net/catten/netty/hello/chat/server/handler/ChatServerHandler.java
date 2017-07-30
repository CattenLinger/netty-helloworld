package net.catten.netty.hello.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Date;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel income = ctx.channel();
        income.writeAndFlush("Welcome to HelloWorld chat server!\r\n");
        if(!channels.isEmpty())
            for (Channel channel : channels)
                channel.writeAndFlush(String.format("[SERVER]: [%s] connected.\r\n", channel.remoteAddress()));
        channels.add(income);
        System.out.printf("[%s] Client %s connected.\r\n",new Date().toString(),income.remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(!channels.isEmpty())
            for (Channel channel : channels)
                channel.writeAndFlush(String.format("[SERVER]: [%s] was disconnected.\r\n", channel.remoteAddress()));
        channels.remove(ctx.channel());
        System.out.printf("[%s] Client %s disconnected.\r\n",new Date().toString(),ctx.channel().remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        if(!channels.isEmpty())
            for (Channel c : channels)
                c.writeAndFlush(String.format("[SERVER]: [%s] online.\r\n", c != currentChannel ? c.remoteAddress() : "yourself"));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        if(!channels.isEmpty())
            for (Channel c : channels)
                c.writeAndFlush(String.format("[SERVER]: [%s] afk.\r\n", c != currentChannel ? c.remoteAddress() : "yourself"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.printf("[%s] Channel %s raise an exception : {%s}(%s)\r\n",
                new Date().toString(),
                ctx.channel().remoteAddress(),
                cause.getClass().getName(),
                cause.getMessage());

        channels.remove(ctx.channel());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel currentChannel = ctx.channel();
        if(!channels.isEmpty())
            for (Channel c : channels){
                boolean self = c == currentChannel;
                c.writeAndFlush(String.format("%s[%s]: %s\n\r",
                        self ? "\b" : "",
                        self ? "yourself" : c.remoteAddress(),
                        msg));
            }
    }
}
