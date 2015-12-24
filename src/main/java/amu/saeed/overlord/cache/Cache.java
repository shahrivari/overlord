package amu.saeed.overlord.cache;

public interface Cache {

    void put(byte[] key, byte[] val);

    byte[] get(byte[] key);

    void delete(byte[] key);

    void updateIfPresent(byte[] key, byte[] val);
}
