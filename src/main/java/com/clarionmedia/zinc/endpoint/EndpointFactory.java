package com.clarionmedia.zinc.endpoint;

/**
 * Provides methods for building {@link Endpoint} instances.
 */
public interface EndpointFactory {

    /**
     * Attempts to construct a client {@code Endpoint} by performing the authentication "handshake." It will request a
     * connection with the given host and send the given token for authentication. If the connection fails or if the
     * token is invalid, {@code null} is returned. Otherwise, an {@code Endpoint} instance is returned.
     *
     * @param hostAddress           the host to attempt to connect to
     * @param hostDiscoveryPort     the host's connection request port
     * @param token                 the authentication token
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use
     * @return {@code Endpoint} instance or {@code null}
     */
    Endpoint buildClientEndpoint(String hostAddress, int hostDiscoveryPort, String token,
                                 MessageHandlerFactory messageHandlerFactory);

    /**
     * Constructs a new {@link HostEndpoint}.
     *
     * @param port                  the port to bind to
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use
     * @return {@code HostEndpoint} instance
     */
    HostEndpoint buildHostEndpoint(int port, MessageHandlerFactory messageHandlerFactory);

    /**
     * Constructs a new {@link HostEndpoint}.
     *
     * @param port                  the port to bind to
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use
     * @param authenticator         the {@link Authenticator} to use to authenticate requests
     * @return {@code HostEndpoint} instance
     */
    HostEndpoint buildHostEndpoint(int port, MessageHandlerFactory messageHandlerFactory,
                                   Authenticator authenticator);
}
