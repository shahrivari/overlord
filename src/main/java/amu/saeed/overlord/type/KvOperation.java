package amu.saeed.overlord.type;

import java.io.*;

public class KvOperation implements Serializable, Externalizable {
    public static final byte GET = (byte) 0x01;
    public static final byte SET = (byte) 0x02;
    public static final byte DELETE = (byte) 0x03;

    private KeyValue keyValue;
    private byte operation;

    private KvOperation() { }

    public KvOperation(byte operation, KeyValue keyValue) {
        this.keyValue = keyValue;
        this.operation = operation;
    }

    public static KvOperation makeGet(KeyValue keyValue) { return new KvOperation(GET, keyValue);}

    public static KvOperation makeSet(KeyValue keyValue) { return new KvOperation(SET, keyValue);}

    public static KvOperation makeDelete(KeyValue keyValue) { return new KvOperation(DELETE, keyValue);}

    public KeyValue getKeyValue() {
        return keyValue;
    }

    public byte getOperation() {
        return operation;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.write(operation);
        keyValue.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        operation = in.readByte();
        keyValue = new KeyValue();
        keyValue.readExternal(in);
    }
}
