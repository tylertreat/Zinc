package com.clarionmedia.zinc.message.impl;

/**
 * Implementation of {@link AbstractMessage} representing a partial fragment of a {@link Document}.
 */
public abstract class DocumentFragment<T> extends AbstractMessage {

    public DocumentFragment() {
        super(MessageType.DOCUMENT_FRAGMENT);
    }

    @Override
    public MessageType getType() {
        return MessageType.DOCUMENT_FRAGMENT;
    }

}