package com.clarionmedia.zinc.endpoint;

/**
 * {@code HostEndpoint} represents the notion of a "host" in a distributed client-host
 * cluster application.
 */
public interface HostEndpoint extends Endpoint {

    /**
     * Adds the given client, identified by an address and port, to the {@code HostEndpoint}.
     * This allows the {@code HostEndpoint} to receive messages from it.
     *
     * @param address the address of the peer to add
     * @param port    the port of the peer to add
     */
    void addClient(String address, int port);

    /**
     * Returns the port that's listening for connection requests.
     *
     * @return connection request port
     */
    int getConnectionRequestPort();

    /**
     * Adds the {@link ClientAddedListener} to the {@code HostEndpoint}.
     *
     * @param listener the {@code ClientAddedListener} to add
     */
    void addClientAddedListener(ClientAddedListener listener);

}
