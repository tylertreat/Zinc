package com.clarionmedia.zinc.endpoint;

/**
 * A {@code Peer} is an {@link Endpoint} identified by an address and a port.
 */
public class Peer {

    private String address;
    private int port;

    public Peer(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

}
