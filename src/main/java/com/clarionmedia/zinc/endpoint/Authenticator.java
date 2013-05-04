package com.clarionmedia.zinc.endpoint;

/**
 * Describes how to authenticate a connection request from a client.
 */
public interface Authenticator {

    /**
     * Indicates if the request from the given IP address and port using the provided token is authentic.
     * If it is, this address will be added as a peer.
     *
     * @param ip    the IP address the connection request came from
     * @param port  the port the connection request came from
     * @param token the token the connection request sent
     * @return {@code true} if the request is authentic, {@code false} if not
     */
    boolean isAuthenticated(String ip, int port, String token);

}
