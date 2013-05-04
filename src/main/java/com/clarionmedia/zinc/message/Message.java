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

package com.clarionmedia.zinc.message;

/**
 * Represents a message sent from an {@link com.clarionmedia.zinc.endpoint.Endpoint}.
 */
public interface Message {

    public static final byte FULL_DOCUMENT_PREFIX = 0x00;
    public static final byte DOCUMENT_FRAGMENT_PREFIX = 0x01;
    public static final byte FULL_DOCUMENT_REQUEST_PREFIX = 0x02;

    public static enum MessageType {
        FULL_DOCUMENT(FULL_DOCUMENT_PREFIX), DOCUMENT_FRAGMENT(DOCUMENT_FRAGMENT_PREFIX),
        FULL_DOCUMENT_REQUEST(FULL_DOCUMENT_REQUEST_PREFIX);

        private byte prefix;

        MessageType(byte prefix) {
            this.prefix = prefix;
        }

        public byte getPrefix() {
            return prefix;
        }
    }

    /**
     * Returns the {@link MessageType} of this {@code Message}.
     *
     * @return {@code MessageType}
     */
    MessageType getType();

    /**
     * Returns the {@code Message} as a byte array prefixed with the channel bytes.
     *
     * @return byte array
     */
    byte[] getPrefixedByteArray();

    /**
     * Returns the raw data of the {@code Message}.
     *
     * @return raw {@code Message} data
     */
    byte[] getData();

    /**
     * Returns the origin endpoint ID for this {@code Message}.
     *
     * @return {@code Endpoint} ID
     */
    String getOrigin();

    /**
     * Sets the origin endpoint ID for this {@code Message}.
     *
     * @param origin {@code Endpoint} ID
     */
    void setOrigin(String origin);

}
