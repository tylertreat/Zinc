package com.clarionmedia.zinc.message.impl;

import com.clarionmedia.zinc.message.Message;

import java.nio.ByteBuffer;

/**
 * Abstract implementation of {@link Message}.
 */
public abstract class AbstractMessage implements Message {

    protected byte[] data;
    protected byte typeMarker;
    protected String origin;

    public AbstractMessage(MessageType messageType) {
        typeMarker = messageType.getPrefix();
    }

    @Override
    public final byte[] getPrefixedByteArray() {
        byte[] originBytes = origin.getBytes();
        if (data == null) {
            byte[] buffer = new byte[originBytes.length + 1];
            ByteBuffer target = ByteBuffer.wrap(buffer);
            target.put(originBytes);
            target.put(typeMarker);
            return target.array();
        }
        byte[] buffer = new byte[originBytes.length + data.length + 1];
        ByteBuffer target = ByteBuffer.wrap(buffer);
        target.put(originBytes);
        target.put(typeMarker);
        target.put(data);
        return target.array();
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public String getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(String origin) {
        this.origin = origin;
    }

}
