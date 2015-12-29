package amu.saeed.overlord;

/**
 * Provides general configuration
 */
public class Configuration {
    int maxCacheableObjectSize = 256 * 1024; // 256 KB
    int l1CacheSize = 2_000;
    String redisAddressUrl = "redis://127.0.0.1:6379";

    public int getMaxCacheableObjectSize() {
        return maxCacheableObjectSize;
    }

    public void setMaxCacheableObjectSize(int maxCacheableObjectSize) {
        this.maxCacheableObjectSize = maxCacheableObjectSize;
    }

    public int getL1CacheSize() {
        return l1CacheSize;
    }

    public void setL1CacheSize(int l1CacheSize) {
        this.l1CacheSize = l1CacheSize;
    }


    public String getRedisAddressUrl() {
        return redisAddressUrl;
    }

    public void setRedisAddressUrl(String redisAddressUrl) {
        this.redisAddressUrl = redisAddressUrl;
    }
}
