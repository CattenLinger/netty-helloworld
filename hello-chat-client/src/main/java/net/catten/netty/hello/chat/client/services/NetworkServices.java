package net.catten.netty.hello.chat.client.services;

import io.netty.channel.Channel;
import net.catten.netty.hello.chat.client.common.EventTarget;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public class NetworkServices {

    private Channel channel;

    private List<EventTarget<String>> eventListeners = new ArrayList<>();

    public void setChannel(Channel channel){
        this.channel = channel;
    }

    public void sendMessage(String message){
        channel.writeAndFlush(message);
    }

    public void handleMessageReceived(String msg) {
        if(!eventListeners.isEmpty()) eventListeners.forEach(e -> e.trig(msg));
    }

    public void registerEventListener(EventTarget<String> listener){
        eventListeners.add(listener);
    }

    public void removeEventListener(EventTarget<String> listener){
        eventListeners.remove(listener);
    }
}
