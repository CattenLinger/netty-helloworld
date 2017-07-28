package net.catten.netty.hello.time.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by Catten Linger on 2017/7/26.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(8);
        time.writeLong(System.currentTimeMillis());

        final ChannelFuture channelFuture = ctx.writeAndFlush(time);
        channelFuture.addListener(future -> {
            assert future == channelFuture;
            ctx.close();
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
