package net.catten.netty.hello.discard.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by Catten Linger on 2017/7/26.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String s = in.toString(Charset.forName("UTF-8"));

        if(s.matches("^ping(\\s)*")) {
            ByteBuf out = ctx.alloc().buffer();
            out.writeBytes("pong\r\n".getBytes());
            ctx.write(out);
            ctx.flush();
        }


        System.out.print(s);
        System.out.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
