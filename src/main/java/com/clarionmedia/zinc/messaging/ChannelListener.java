package com.clarionmedia.zinc.messaging;

/**
 * A {@code ChannelListener} is responsible for listening for incoming messages and delegating them to a
 * {@link MessageHandler}.
 */
public interface ChannelListener {

    /**
     * Adds the given peer, identified by an address and port, to the {@code ChannelListener}.
     *
     * @param address the address of the peer to add
     * @param port    the port of the peer to add
     */
    void addPeer(String address, int port);

    /**
     * Notifies the {@code ChannelListener} to start listening.
     */
    void listen();

    /**
     * Notifies the {@code ChannelListener} to stop listening and to release any system resources.
     */
    void terminate();

}
