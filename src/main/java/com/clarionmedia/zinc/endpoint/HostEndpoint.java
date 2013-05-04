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
