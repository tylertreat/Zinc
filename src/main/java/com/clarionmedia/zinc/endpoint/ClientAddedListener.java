package com.clarionmedia.zinc.endpoint;

/**
 * Event listener that listens for when clients are added to a {@link HostEndpoint}.
 */
public interface ClientAddedListener {

    /**
     * Invoked when a client is added to the {@link HostEndpoint}.
     *
     * @param address the client address
     * @param port the client port
     */
    void onClientAdded(String address, int port);

}
