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
