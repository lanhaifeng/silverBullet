package com.tianque.cache;

import com.sun.istack.internal.NotNull;
import reactor.util.annotation.Nullable;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2018-12-21 10:27
 */
public interface Cache<K,V>{

    @Nullable
    void set(K key,V value);

    @Nullable
    void set(K key,V value,int expire);

    @NotNull
    V get(K key);

}
