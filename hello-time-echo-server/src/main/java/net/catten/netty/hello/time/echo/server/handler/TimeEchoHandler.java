package net.catten.netty.hello.time.echo.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.time.LocalTime;
import java.util.Date;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public class TimeEchoHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes("You connected to an time echo server!\r\n".getBytes());
        ctx.writeAndFlush(byteBuf);
        System.out.println("A client connected.");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.writeAndFlush(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            switch (((IdleStateEvent)evt).state()){
                case READER_IDLE:
                case ALL_IDLE:
                    String now = new Date().toString();
                    System.out.println("Read timeout, echoing : " + now);

                    ByteBuf byteBuf = ctx.alloc().buffer();
                    byteBuf.writeBytes((now + "\r\n").getBytes());
                    ctx.writeAndFlush(byteBuf);
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.printf("[%s]A channel raise an exception: [%s]%s.\n", new Date().toString(), cause.getClass().getName(), cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.printf("[%s]A client disconnect.",new Date().toString());
    }
}
