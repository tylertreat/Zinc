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

import com.clarionmedia.zinc.message.Message;

/**
 * An {@code Endpoint} is a single node in the application cluster. It has the ability to both transmit and receive
 * messages.
 */
public interface Endpoint {

    public static final String SCHEME = "tcp://";

    /**
     * Opens an outbound channel for transmitting messages. This method is idempotent, meaning it has no effect if an
     * outbound channel is already open.
     */
    void openOutboundChannel();

    /**
     * Closes the outbound channel, releasing any sockets or other system resources. This method is idempotent, meaning
     * it has no effect if an outbound channel is not open.
     */
    void closeOutboundChannel();

    /**
     * Opens an inbound channel for receiving messages. This method is idempotent, meaning it has no effect if an
     * inbound channel is already open.
     */
    void openInboundChannel();

    /**
     * Closes the inbound channel, releasing any sockets or other system resources. This method is idempotent, meaning
     * it has no effect if an inbound channel is not open.
     */
    void closeInboundChannel();

    /**
     * Transmits the given {@link com.clarionmedia.zinc.message.Message}.
     *
     * @param message the {@code Message} to send
     * @return {@code true} if the send was successful, {@code false} otherwise
     */
    boolean send(Message message);

    /**
     * Returns the port this {@code Endpoint} is bound to.
     *
     * @return port
     */
    int getPort();

    /**
     * Indicates if this {@code Endpoint} is a {@link HostEndpoint}.
     *
     * @return {@code true} if it is a host, {@code false} otherwise
     */
    boolean isHost();

    /**
     * Sends a request for the full document state.
     *
     * @return {@code true} if the request was successful, {@code false} otherwise
     */
    boolean requestFullDocument();

    /**
     * Returns the GUID for this {@code Endpoint}.
     *
     * @return {@code Endpoint} GUID
     */
    String getId();

}
