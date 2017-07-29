package net.catten.netty.hello.chat.client.common;

import java.util.HashMap;

/**
 * Created by cattenlinger on 2017/7/7.
 */
public class MiniSpring {

    private static HashMap<String, MBean<?>> container = new HashMap<>();

    public static <M> void putBean(String name, M bean) {
        container.put(name, new MBean<>(bean));
    }

    @SuppressWarnings("unchecked")
    public static <M> M getBean(String name) {
        MBean<?> baseMBean = container.get(name);
        return (M) baseMBean.getBeanType().cast(baseMBean.getObject());
    }

    public static void configureBeans(MConfiguring configuring) {
        try {
            configuring.configure();
        } catch (Exception e) {
            System.out.printf("Error when creating beans, reason : {%s}%s", e.getClass().getName(), e.getCause());
        }
    }

    public static void destroy() {
        container.clear();
    }

    public static void destroy(String beanName) {
        container.remove(beanName);
    }

    private static class MBean<T> {
        private Class<?> tClass;
        private T object;

        MBean(T object) {
            tClass = object.getClass();
            this.object = object;
        }

        public T getObject() {
            return object;
        }

        public Class<?> getBeanType() {
            return tClass;
        }
    }

    public interface MConfiguring {
        void configure() throws Exception;
    }
}
