/*
 * Copyright (C) 2013 Tyler Treat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarionmedia.zinc.endpoint;

import com.clarionmedia.zinc.message.Message;
import com.clarionmedia.zinc.message.Message.MessageType;

/**
 * A {@code MessageHandler} is used to react to a message that has been received by an {@link Endpoint}.
 * It implements {@link Runnable} so that it can be executed within the context of a worker thread
 * asynchronously.
 */
public abstract class MessageHandler implements Runnable {

    private final byte[] message;
    protected Endpoint endpoint;

    /**
     * Creates a new {@code MessageHandler} instance.
     *
     * @param message the message to handle
     */
    public MessageHandler(Endpoint endpoint, byte[] message) {
        this.endpoint = endpoint;
        this.message = message;
    }

    /**
     * Processes the message asynchronously.
     *
     * @param message     the message to process
     * @param pos         the starting index of the real payload, normally 1
     * @param origin      the origin of the message
     * @param messageType the {@link MessageType} of the message to handle
     */
    protected abstract void handle(byte[] message, int pos, String origin, MessageType messageType);

    @Override
    public final void run() {
        if (message.length == 0)
            return;
        byte[] originBytes = new byte[36];
        System.arraycopy(message, 0, originBytes, 0, 36);
        String origin = new String(originBytes);
        if (origin.equals(endpoint.getId())) {
            // Message is an echo, ignore it
            return;
        }
        byte marker = message[36];
        MessageType type;
        switch (marker) {
            case Message.FULL_DOCUMENT_PREFIX:
                type = MessageType.FULL_DOCUMENT;
                break;
            case Message.DOCUMENT_FRAGMENT_PREFIX:
                type = MessageType.DOCUMENT_FRAGMENT;
                break;
            case Message.FULL_DOCUMENT_REQUEST_PREFIX:
                type = MessageType.FULL_DOCUMENT_REQUEST;
                break;
            default:
                throw new RuntimeException("Invalid message type typeMarker " + marker);
        }
        handle(message, originBytes.length + 1, origin, type);
    }

}