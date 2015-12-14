package amu.saeed.overlord.type;

import java.util.Arrays;

public class ByteArrayWrapper {
    private final byte[] array;
    private int hashCode = 0;

    public ByteArrayWrapper(byte[] array) {
        if (array == null)
            throw new IllegalArgumentException("Array cannot be null!");
        this.array = array;
    }

    public byte[] getArray() {
        return array;
    }

    public int length() {
        return array.length;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ByteArrayWrapper byteArray = (ByteArrayWrapper) o;
        return Arrays.equals(array, byteArray.array);
    }

    @Override public int hashCode() {
        if (hashCode == 0)
            hashCode = Arrays.hashCode(array);
        return hashCode;
    }
}
