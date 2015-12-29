package amu.saeed.overlord.type;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

public class ByteArrayWrapper implements Externalizable {
    private byte[] array;
    private int hashCode = 0;

    public ByteArrayWrapper() {
    }

    public ByteArrayWrapper(byte[] array) {
        if (array == null) throw new IllegalArgumentException("Array cannot be null!");
        this.array = array;
    }

    public byte[] getArray() {
        return array;
    }

    public int length() {
        return array.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteArrayWrapper byteArray = (ByteArrayWrapper) o;
        return Arrays.equals(array, byteArray.array);
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) hashCode = Arrays.hashCode(array);
        return hashCode;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(array.length);
        out.write(array);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        array = new byte[in.readInt()];
        in.read(array);
    }
}
