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
