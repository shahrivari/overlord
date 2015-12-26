package amu.saeed.overlord.cache;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.codec.RedisCodec;

import java.nio.ByteBuffer;

/**
 * Created by Saeed on 12/26/2015.
 */
public class RedisCache implements Cache {
    RedisClient client;
    StatefulRedisConnection<byte[], byte[]> connection;

    public RedisCache() {
        client = RedisClient.create("redis://localhost:6379");
        connection = client.connect(new RedisCodec<byte[], byte[]>() {
            @Override public byte[] decodeKey(ByteBuffer byteBuffer) {
                return byteBuffer.array();
            }

            @Override public byte[] decodeValue(ByteBuffer byteBuffer) {
                return byteBuffer.array();
            }

            @Override public ByteBuffer encodeKey(byte[] bytes) {
                return ByteBuffer.wrap(bytes);
            }

            @Override public ByteBuffer encodeValue(byte[] bytes) {
                return ByteBuffer.wrap(bytes);
            }
        });
    }

    @Override public void put(byte[] key, byte[] val) {
        connection.sync().set(key, val);
    }

    @Override public byte[] get(byte[] key) {
        return connection.sync().get(key);
    }

    @Override public void delete(byte[] key) {
        connection.sync().del(key);
    }

    @Override public void updateIfPresent(byte[] key, byte[] val) {
        connection.sync().set(key, val);
    }
}
