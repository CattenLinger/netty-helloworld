package net.catten.netty.hello.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import javafx.application.Application;
import javafx.stage.Stage;
import net.catten.netty.hello.chat.client.common.MiniSpring;
import net.catten.netty.hello.chat.client.common.Services;
import net.catten.netty.hello.chat.client.handler.ChatClientHandler;
import net.catten.netty.hello.chat.client.services.NetworkServices;
import net.catten.netty.hello.chat.client.services.ViewServices;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public class MainApp extends Application{
    public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {

        String server = "127.0.0.1";
        int port = 2325;

        if(args.length > 0){
            server = args[0];
            port = Integer.parseInt(args[1]);
        }

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("businessHandler",new ChatClientHandler());
                        }
                    });

            Services.Network.init();
            NetworkServices networkServices = Services.Network.get();
            if (networkServices != null) {
                networkServices.setChannel(bootstrap.connect(server, port).sync().channel());
                launch(args);
            } else {
                throw new NullPointerException("Could not initialize network services.");
            }
        }finally {
            group.shutdownGracefully();
        }}

    @Override
    public void start(Stage primaryStage) throws Exception {
        Services.View.init();
        ViewServices viewServices = Services.View.get();

        primaryStage.setTitle("HelloWorld Chat Client");

        if (viewServices != null) {
            viewServices.setMainStage(primaryStage);
            viewServices.launchMainWindow();
        }else{
            System.out.println("Could not initialize ui, closing");
            System.exit(-1);
        }
    }
}
