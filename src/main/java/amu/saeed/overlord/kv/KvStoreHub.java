package amu.saeed.overlord.kv;

import amu.saeed.overlord.Configuration;
import amu.saeed.overlord.cache.Cache;
import amu.saeed.overlord.cache.LocalCache;
import amu.saeed.overlord.cache.RedisCache;

public class KvStoreHub {
    final private Cache localCache;
    final private Cache inMemCache;

    public KvStoreHub(Configuration conf) {
        localCache = new LocalCache(conf.getL1CacheSize());
        inMemCache = new RedisCache(conf.getRedisAddressUrl());
    }

    public void put(long key, byte[] val) {
        // put the data in backing store

        // put the data in inMem
        inMemCache.updateIfPresent(key, val);

        // update localCache
        localCache.updateIfPresent(key, val);
    }

    public byte[] get(long key) {
        byte[] value = null;
        // try get from local cache
        value = localCache.get(key);
        if (value != null) return value;

        // the key is not in local cache. Try Redis...
        value = inMemCache.get(key);
        if (value != null) return value;


        // the key is not in Redis. Try the backing store

        // there is not such key in system! Return null;
        return null;
    }

    public void delete(long key) {
        // delete the key from backing store

        // delete the key from Redis
        inMemCache.delete(key);

        // delete the key from localCache
        localCache.delete(key);
    }
}
