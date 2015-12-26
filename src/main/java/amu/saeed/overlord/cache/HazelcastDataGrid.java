package amu.saeed.overlord.cache;

import amu.saeed.overlord.type.ByteArrayWrapper;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class HazelcastDataGrid implements Cache {
    Map<ByteArrayWrapper, byte[]> l2Cache;

    public HazelcastDataGrid() {
        Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        l2Cache = instance.getMap("l2");
    }

    @Override public void put(byte[] key, byte[] val) {
        l2Cache.put(new ByteArrayWrapper(key), val);
    }

    @Override public byte[] get(byte[] key) {
        Object obj = l2Cache.get(new ByteArrayWrapper(key));
        if (obj != null)
            return (byte[]) obj;
        else
            return null;
    }

    @Override public void delete(byte[] key) {
        l2Cache.remove(new ByteArrayWrapper(key));
    }

    @Override public void updateIfPresent(byte[] key, byte[] val) {
        ByteArrayWrapper key_ = new ByteArrayWrapper(key);
        if (l2Cache.get(key_) != null)
            l2Cache.put(key_, val);
    }
}
