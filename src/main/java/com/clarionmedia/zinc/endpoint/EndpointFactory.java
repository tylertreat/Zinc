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
