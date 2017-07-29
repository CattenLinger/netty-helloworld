package net.catten.netty.hello.chat.client.common;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public interface EventTarget<T> {
    void trig(T o);
}
