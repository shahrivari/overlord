package amu.saeed.overlord.cache;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.codec.RedisCodec;

import java.nio.ByteBuffer;

/**
 * Created by Saeed on 12/26/2015.
 */
public class RedisCache implements Cache {
    RedisClient client;
    RedisConnection<byte[], byte[]> connection;

    public RedisCache() {
        this.client = new RedisClient("127.0.0.1");
        connection = client.connect(new RedisCodec<byte[], byte[]>() {
            @Override public byte[] decodeKey(ByteBuffer byteBuffer) {
                return byteBuffer.array();
            }

            @Override public byte[] decodeValue(ByteBuffer byteBuffer) {
                return byteBuffer.array();
            }

            @Override public byte[] encodeKey(byte[] bytes) {
                return bytes;
            }

            @Override public byte[] encodeValue(byte[] bytes) {
                return bytes;
            }
        });
    }

    @Override public void put(byte[] key, byte[] val) {
        connection.set(key, val);
    }

    @Override public byte[] get(byte[] key) {
        return connection.get(key);
    }

    @Override public void delete(byte[] key) {
        connection.del(key);
    }

    @Override public void updateIfPresent(byte[] key, byte[] val) {
        connection.set(key, val);
    }
}
