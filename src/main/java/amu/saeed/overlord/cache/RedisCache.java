package amu.saeed.overlord.cache;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.codec.RedisCodec;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

/**
 * Created by Saeed on 12/26/2015.
 */
public class RedisCache implements Cache {
    RedisClient client;
    StatefulRedisConnection<Long, byte[]> connection;

    public RedisCache(String url) {
        client = RedisClient.create(url);
        connection = client.connect(new RedisCodec<Long, byte[]>(){
            @Override
            public Long decodeKey(ByteBuffer byteBuffer) {
                return byteBuffer.getLong();
            }

            @Override
            public byte[] decodeValue(ByteBuffer byteBuffer) {
                return byteBuffer.array();
            }

            @Override
            public ByteBuffer encodeKey(Long aLong) {
                ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
                buf.putLong(aLong);
                return buf;
            }

            @Override
            public ByteBuffer encodeValue(byte[] bytes) {
                return ByteBuffer.wrap(bytes);
            }
        });
    }

    @Override
    public void put(long key, byte[] val) {
        connection.sync().set(key, val);
    }

    @Override
    public byte[] get(long key) {
        return connection.sync().get(key);
    }

    @Override
    public void delete(long key) {
        connection.sync().del(key);
    }

    @Override
    public void updateIfPresent(long key, byte[] val) {
        connection.sync().set(key, val);
    }
}
