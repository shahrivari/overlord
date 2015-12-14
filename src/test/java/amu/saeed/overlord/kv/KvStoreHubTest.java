package amu.saeed.overlord.kv;

import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class KvStoreHubTest {
    /**
     * Test the performance of KvStoreHub
     */
    @Test public void pressureTest() throws Exception {
        int itemsCount = 100_000;
        KvStoreHub hub = new KvStoreHub(itemsCount);
        Random random = new Random();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < itemsCount; i++)
            if (random.nextInt() % 2 == 0)
                hub.put(Integer.toBinaryString(random.nextInt()).getBytes(),
                    Integer.toBinaryString(random.nextInt()).getBytes());
            else
                hub.delete(Integer.toBinaryString(random.nextInt()).getBytes());

        stopwatch.stop();
        System.out.printf("%,d put/deletes took %s", itemsCount, stopwatch);
        Assert.assertTrue(stopwatch.elapsed(TimeUnit.SECONDS) < itemsCount / 1000);
    }


} 
