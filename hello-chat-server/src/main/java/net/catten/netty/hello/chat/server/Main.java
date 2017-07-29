package net.catten.netty.hello.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import net.catten.netty.hello.chat.server.handler.ChatServerHandler;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public class Main {
    public static void main(String[] args) {

        int port = 2325;

        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());

                            ch.pipeline().addLast("businessHandler",new ChatServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            System.out.println("Server startup.");
            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.printf("Server raise an exception : {%s}%s\n",e.getClass(),e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("Server shutdown");
        }
    }
}
