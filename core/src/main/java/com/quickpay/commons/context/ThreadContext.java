package com.quickpay.commons.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ThreadContext {
    private static final ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    /**
     *
     * @param key
     * @return
     * @param <T>
     *     throws CastException
     */
    @SuppressWarnings("unchecked")
    public static  <T> T get(ContextKey key){
       return (T) threadLocal.get().get(key.name());
    }

    /**
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value){
        threadLocal.get().put(key, value);
    }

    public static void close(){
        threadLocal.remove();
    }
}
