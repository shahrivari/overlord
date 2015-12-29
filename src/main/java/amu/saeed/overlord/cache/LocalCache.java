package amu.saeed.overlord.cache;

import com.google.common.cache.CacheBuilder;

public class LocalCache implements Cache {
    final private com.google.common.cache.Cache<Long, byte[]> localCache;

    public LocalCache(int size) {
        localCache = CacheBuilder.newBuilder().recordStats().concurrencyLevel(Runtime.getRuntime()
                .availableProcessors() * 2).maximumSize(size).build();
    }

    @Override
    public void put(long key, byte[] val) {
        localCache.put(key, val);
    }

    @Override
    public byte[] get(long key) {
        return localCache.getIfPresent(key);
    }

    @Override
    public void delete(long key) {
        localCache.invalidate(key);
    }

    @Override
    public void updateIfPresent(long key, byte[] val) {
        if (localCache.getIfPresent(key) != null) localCache.put(key, val);
    }
}
