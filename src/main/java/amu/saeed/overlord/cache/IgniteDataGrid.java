package amu.saeed.overlord.cache;

import amu.saeed.overlord.type.ByteArrayWrapper;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

/**
 * Created by saeed on 12/24/15.
 */
public class IgniteDataGrid implements Cache {
    Ignite ignite;
    IgniteCache igniteCache;


    public IgniteDataGrid() {
        IgniteConfiguration conf = new IgniteConfiguration();
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        cacheConfiguration.setStartSize(10_000);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        cacheConfiguration.setOffHeapMaxMemory(0);
        cacheConfiguration.setSwapEnabled(false);
        cacheConfiguration.setName("inmem");
        conf.setCacheConfiguration(cacheConfiguration);
        ignite = Ignition.start(conf);
        igniteCache = ignite.getOrCreateCache(cacheConfiguration);
    }

    @Override
    public void put(byte[] key, byte[] val) {
        igniteCache.put(new ByteArrayWrapper(key), new ByteArrayWrapper(val));
    }

    @Override
    public byte[] get(byte[] key) {
        Object obj = igniteCache.get(new ByteArrayWrapper(key));
        if (obj != null)
            return ((ByteArrayWrapper) obj).getArray();
        else
            return null;
    }

    @Override
    public void delete(byte[] key) {
        igniteCache.clear(new ByteArrayWrapper(key));
    }

    @Override
    public void updateIfPresent(byte[] key, byte[] val) {
        ByteArrayWrapper key_ = new ByteArrayWrapper(key);
        if (igniteCache.get(key_) != null)
            igniteCache.put(key_, new ByteArrayWrapper(val));
    }
}
