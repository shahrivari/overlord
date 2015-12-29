package amu.saeed.overlord.kv;

import amu.saeed.overlord.Configuration;
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
        KvStoreHub hub = new KvStoreHub(new Configuration());
        Random random = new Random();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < itemsCount; i++)
            if (random.nextInt() % 2 == 0)
                hub.put(random.nextLong(),
                    Integer.toBinaryString(random.nextInt()).getBytes());
            else
                hub.delete(random.nextLong());

        stopwatch.stop();
        System.out.printf("%,d put/deletes took %s", itemsCount, stopwatch);
        Assert.assertTrue(stopwatch.elapsed(TimeUnit.SECONDS) < itemsCount / 1000);
    }


} 
