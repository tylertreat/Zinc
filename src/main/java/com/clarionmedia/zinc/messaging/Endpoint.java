package com.clarionmedia.zinc.messaging;

/**
 * An {@code Endpoint} is a single node in the application cluster. It has the ability to both transmit and receive
 * messages.
 */
public interface Endpoint {

    public static final String SCHEME = "tcp://";

    /**
     * Opens an outbound channel for transmitting messages. This method is idempotent, meaning it has no effect if an
     * outbound channel is already open.
     */
    void openOutboundChannel();

    /**
     * Closes the outbound channel, releasing any sockets or other system resources. This method is idempotent, meaning
     * it has no effect if an outbound channel is not open.
     */
    void closeOutboundChannel();

    /**
     * Opens an inbound channel for receiving messages. This method is idempotent, meaning it has no effect if an
     * inbound channel is already open.
     */
    void openInboundChannel();

    /**
     * Closes the inbound channel, releasing any sockets or other system resources. This method is idempotent, meaning
     * it has no effect if an inbound channel is not open.
     */
    void closeInboundChannel();

    /**
     * Transmits the given {@link Message}.
     *
     * @param message the {@code Message} to send
     * @return {@code true} if the send was successful, {@code false} otherwise
     */
    boolean send(Message message);

    /**
     * Returns the port this {@code Endpoint} is bound to.
     *
     * @return port
     */
    int getPort();

    /**
     * Indicates if this {@code Endpoint} is a {@link HostEndpoint}.
     *
     * @return {@code true} if it is a host, {@code false} otherwise
     */
    boolean isHost();

    /**
     * Sends a request for the full document state.
     *
     * @return {@code true} if the request was successful, {@code false} otherwise
     */
    boolean requestFullDocument();

    /**
     * Returns the GUID for this {@code Endpoint}.
     *
     * @return {@code Endpoint} GUID
     */
    String getId();

}
