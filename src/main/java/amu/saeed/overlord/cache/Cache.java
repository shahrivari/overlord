package amu.saeed.overlord.cache;

public interface Cache {

    void put(long key, byte[] val);

    byte[] get(long key);

    void delete(long key);

    void updateIfPresent(long key, byte[] val);
}
