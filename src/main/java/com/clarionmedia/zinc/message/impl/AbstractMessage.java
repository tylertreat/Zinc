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
