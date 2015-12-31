package amu.saeed.overlord.type;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.*;
import java.util.Base64;

public class KeyValue implements Serializable, Externalizable {
    public static final byte VERSION = 1;
    private static final Base64.Encoder ENCODER_64 = Base64.getEncoder();
    private long key;
    private byte[] value;
    public KeyValue() {
        key = 0L;
        value = new byte[0];
    }



    public KeyValue(long key, byte[] value) {
        this.key = key;
        this.value = value;
    }

    public long getKey() {
        return key;
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.write(VERSION);
        out.writeLong(value.length);
        out.writeInt(value.length);
        out.write(value);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte version = in.readByte();
        if (version == 1) {
            key = in.readLong();
            value = new byte[in.readInt()];
            in.read(value);
        }
        throw new IllegalStateException("Bad version: " + version);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder((value.length + 10) * 4 / 3);
        builder.append("{\n\"key\":\"").append(key).append("\",\n\"value\":\"")
                .append(ENCODER_64.encodeToString(value)).append("\"\n}");
        return builder.toString();
    }

    public ByteArrayOutputStream toMessagePackStream() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MessagePacker packer = MessagePack.newDefaultPacker(stream);
        try {
            packer.packByte(VERSION);
            packer.packLong(key);
            packer.packInt(value.length);
            packer.writePayload(value);
            packer.close();
            return stream;
        } catch (IOException e) {
            throw new IllegalStateException("Error while packing.", e);
        }
    }

    public byte[] toMessagePackArray() {
        return toMessagePackStream().toByteArray();
    }

    public KeyValue fromMessagePack(byte[] bytes) {
        try {
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bytes);
            byte version = unpacker.unpackByte();
            if (version == 1) {
                key = unpacker.unpackLong();
                value = new byte[unpacker.unpackInt()];
                unpacker.readPayload(value);
                return this;
            }
            throw new IllegalStateException("Bad version: " + version);
        } catch (IOException e) {
            throw new IllegalStateException("Error while unpacking.", e);
        }
    }
}
