package amu.saeed.overlord.kv;

import amu.saeed.overlord.cache.Cache;
import amu.saeed.overlord.cache.RedisCache;
import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheTest {
    /**
     * Test the performance of KvStoreHub
     */
    @Test public void pressureTest() throws Exception {
        int itemsCount = 1_000_000;
        AtomicInteger remaining = new AtomicInteger(itemsCount);
        Cache cache = new RedisCache("redis://localhost:6379");
        Random random = new Random();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread[] threads = new Thread[40];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                while (remaining.decrementAndGet() > 0)
                    if (random.nextInt() % 2 == 0)
                        cache.put(random.nextLong(),
                            Integer.toBinaryString(random.nextInt()).getBytes());
                    else
                        cache.delete(random.nextLong());
            });
            threads[i].start();
        }

        for (Thread thread : threads)
            thread.join();


        stopwatch.stop();
        System.out.printf("%,d put/deletes took %s", itemsCount, stopwatch);
        Assert.assertTrue(stopwatch.elapsed(TimeUnit.SECONDS) < itemsCount / 1000);
    }


} 
