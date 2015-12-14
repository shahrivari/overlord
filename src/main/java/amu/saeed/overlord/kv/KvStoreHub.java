package amu.saeed.overlord.kv;

import amu.saeed.overlord.type.ByteArrayWrapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class KvStoreHub {
    final private Cache<ByteArrayWrapper, byte[]> localCache;

    public KvStoreHub(int localCacheSize) {
        localCache =
            CacheBuilder.newBuilder().recordStats().concurrencyLevel(Runtime.getRuntime().availableProcessors() * 2)
                .maximumSize(10_000).build();
    }

    public void put(byte[] key, byte[] val) {
        // put the data in backing store

        // put the data in Redis

        // update localCache

        localCache.put(new ByteArrayWrapper(key), val);
    }

    public byte[] get(byte[] key) {
        byte[] value = null;
        // try get from local cache
        value = localCache.getIfPresent(new ByteArrayWrapper(key));
        if (value != null)
            return value;

        // the key is not in local cache. Try Redis...


        // the key is not in Redis. Try the backing store

        // there is not such key in system! Return null;
        return null;
    }

    public void delete(byte[] key) {
        // delete the key from backing store

        // delete the key from Redis

        // delete the key from localCache
        localCache.invalidate(new ByteArrayWrapper(key));
    }
}
