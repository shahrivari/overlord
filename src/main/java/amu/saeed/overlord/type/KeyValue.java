package amu.saeed.overlord.type;

import java.io.*;
import java.util.Base64;

public class KeyValue implements Serializable, Externalizable {
    private byte[] key;
    private byte[] value;
    private Base64.Encoder encoder64 = Base64.getEncoder();

    public KeyValue() {
        key = new byte[0];
        value = new byte[0];
    }

    public KeyValue(byte[] key, byte[] value) {
        this.key = key;
        this.value = value;
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(key.length);
        out.writeInt(value.length);
        out.write(key);
        out.write(value);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        key = new byte[in.readInt()];
        value = new byte[in.readInt()];
        in.read(key);
        in.read(value);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder((key.length + value.length) * 4 / 3);
        builder.append("{\n\"key\":\"").append(encoder64.encodeToString(key)).append("\",\n\"value\":\"").append
            (encoder64.encodeToString(value)).append("\"\n}");
        return builder.toString();
    }
}
