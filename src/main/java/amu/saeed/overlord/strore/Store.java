package amu.saeed.overlord.strore;

import java.io.IOException;

public interface Store {

    void put(long key, byte[] val) throws IOException;

    byte[] get(long key) throws IOException;

    void delete(long key) throws IOException;
}
