package net.catten.netty.hello.chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.catten.netty.hello.chat.client.common.Services;
import net.catten.netty.hello.chat.client.services.NetworkServices;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    private NetworkServices networkServices;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        networkServices = Services.Network.get();
        if (networkServices == null) {
            throw new NullPointerException("Could not initialize network services.");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        networkServices.handleMessageReceived(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.printf("Channel raised an exception. {%s}%s \n", cause.getClass().getName(), cause.getMessage());
        ctx.close();
    }
}
