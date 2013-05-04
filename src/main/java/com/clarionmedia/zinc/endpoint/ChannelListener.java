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
