package amu.saeed.overlord.cache;

import amu.saeed.overlord.type.ByteArrayWrapper;
import com.google.common.cache.CacheBuilder;

public class LocalCache implements Cache {

    final private com.google.common.cache.Cache<ByteArrayWrapper, byte[]> localCache;

    public LocalCache() {
        localCache = CacheBuilder.newBuilder().recordStats()
                .concurrencyLevel(Runtime.getRuntime().availableProcessors() * 2).maximumSize(1_000).build();
    }

    @Override
    public void put(byte[] key, byte[] val) {
        localCache.put(new ByteArrayWrapper(key), val);
    }

    @Override
    public byte[] get(byte[] key) {
        return localCache.getIfPresent(new ByteArrayWrapper(key));
    }

    @Override
    public void delete(byte[] key) {
        localCache.invalidate(new ByteArrayWrapper(key));
    }

    @Override
    public void updateIfPresent(byte[] key, byte[] val) {
        ByteArrayWrapper key_ = new ByteArrayWrapper(key);
        if (localCache.getIfPresent(key_) != null)
            localCache.put(key_, val);
    }
}
