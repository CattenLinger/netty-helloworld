package net.catten.netty.hello.chat.client.common;

import net.catten.netty.hello.chat.client.services.NetworkServices;
import net.catten.netty.hello.chat.client.services.ViewServices;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public enum Services {
    View("viewServices", ViewServices.class),
    Network("networkServices", NetworkServices.class);

    private String key;
    private Class<?> aClass;

    Services(String key, Class<?> aClass){
        this.key = key;
        this.aClass = aClass;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(){
        Object o = MiniSpring.getBean(key);
        if (o == null) try {
            MiniSpring.putBean(key,aClass.newInstance());
            return (T) MiniSpring.getBean(key);
        } catch (Throwable ignore) {
            return null;
        }

        return (T)o;
    }

    public void init() throws IllegalAccessException, InstantiationException {
        MiniSpring.putBean(key,aClass.newInstance());
    }
}
