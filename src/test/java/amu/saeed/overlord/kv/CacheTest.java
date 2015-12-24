package amu.saeed.overlord.kv;

import amu.saeed.overlord.cache.Cache;
import amu.saeed.overlord.cache.IgniteDataGrid;
import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CacheTest {
    /**
     * Test the performance of KvStoreHub
     */
    @Test public void pressureTest() throws Exception {
        int itemsCount = 10_000_000;
        Cache cache = new IgniteDataGrid();
        Random random = new Random();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < itemsCount; i++)
            if (random.nextInt() % 2 == 0)
                cache.put(Integer.toBinaryString(random.nextInt()).getBytes(),
                    Integer.toBinaryString(random.nextInt()).getBytes());
            else
                cache.delete(Integer.toBinaryString(random.nextInt()).getBytes());

        stopwatch.stop();
        System.out.printf("%,d put/deletes took %s", itemsCount, stopwatch);
        Assert.assertTrue(stopwatch.elapsed(TimeUnit.SECONDS) < itemsCount / 1000);
    }


} 
