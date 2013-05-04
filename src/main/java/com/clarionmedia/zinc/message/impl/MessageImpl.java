package com.clarionmedia.zinc.message.impl;

/**
 * Implementation of {@link AbstractMessage} representing a generic message.
 */
public class MessageImpl extends AbstractMessage {

    private MessageType messageType;

    public MessageImpl(MessageType messageType) {
        super(messageType);
        this.messageType = messageType;
    }

    @Override
    public MessageType getType() {
        return messageType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
