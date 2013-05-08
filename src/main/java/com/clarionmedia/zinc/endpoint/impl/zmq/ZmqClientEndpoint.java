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

package com.clarionmedia.zinc.endpoint.impl.zmq;

import com.clarionmedia.zinc.endpoint.MessageHandlerFactory;
import com.clarionmedia.zinc.message.Message;
import com.clarionmedia.zinc.message.impl.MessageImpl;
import org.zeromq.ZMQ;

/**
 * Implementation of {@link com.clarionmedia.zinc.endpoint.Endpoint} which represents the notion of a "client" in a distributed client-host
 * cluster application. This implementation relies on ZeroMQ for communication.
 */
public class ZmqClientEndpoint extends AbstractZmqEndpoint {

    /**
     * Creates a new {@code ZmqClientEndpoint} instance.
     *
     * @param hostAddress           the host address to connect to
     * @param hostPort              the host port to connect to
     * @param port                  the port to bind to for sending messages
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use for handling incoming messages
     */
    public ZmqClientEndpoint(String hostAddress, int hostPort, int port, MessageHandlerFactory messageHandlerFactory) {
        super(hostAddress, hostPort, port, ZMQ.SUB, messageHandlerFactory);
    }

    @Override
    protected ZMQ.Socket getSocket(ZMQ.Context context) {
        return context.socket(ZMQ.PUSH);
    }

    @Override
    public boolean isHost() {
        return false;
    }

    @Override
    public boolean requestFullDocument() {
        return send(new MessageImpl(Message.MessageType.FULL_DOCUMENT_REQUEST));
    }
}
