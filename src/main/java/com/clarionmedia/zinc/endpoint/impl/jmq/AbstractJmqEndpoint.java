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

package com.clarionmedia.zinc.endpoint.impl.jmq;

import com.clarionmedia.zinc.endpoint.Endpoint;
import com.clarionmedia.zinc.endpoint.MessageHandlerFactory;
import com.clarionmedia.zinc.endpoint.impl.AbstractChannelListener;
import com.clarionmedia.zinc.endpoint.impl.zmq.ZmqChannelListener;
import com.clarionmedia.zinc.message.Message;
import org.jeromq.ZMQ;

import java.util.UUID;

/**
 * Abstract implementation of {@link com.clarionmedia.zinc.endpoint.Endpoint} containing common code for JeroMQ
 * implementations.
 */
public abstract class AbstractJmqEndpoint implements Endpoint {

    protected AbstractChannelListener abstractChannelListener;
    protected ZMQ.Socket socket;
    protected int port;
    protected String id;
    private ZMQ.Context context;

    /**
     * Creates a new {@code AbstractZmqEndpoint} instance.
     *
     * @param hostAddress           the host address to connect to
     * @param hostPort              the host port to connect to
     * @param port                  the port to bind to for sending messages
     * @param type                  the ZMQ socket type
     * @param messageHandlerFactory the {@link com.clarionmedia.zinc.endpoint.MessageHandlerFactory} to use
     */
    public AbstractJmqEndpoint(String hostAddress, int hostPort, int port, int type, MessageHandlerFactory
            messageHandlerFactory) {
        this.abstractChannelListener = new ZmqChannelListener(this, hostAddress, hostPort, type, messageHandlerFactory);
        this.port = port;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Creates a new {@code AbstractZmqEndpoint} instance.
     *
     * @param type                  the ZMQ socket type
     * @param messageHandlerFactory the {@link com.clarionmedia.zinc.endpoint.MessageHandlerFactory} to use
     */
    public AbstractJmqEndpoint(int port, int type, MessageHandlerFactory messageHandlerFactory) {
        this.abstractChannelListener = new ZmqChannelListener(this, type, messageHandlerFactory);
        this.port = port;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Creates a {@link org.zeromq.ZMQ.Socket} for use by the {@link com.clarionmedia.zinc.endpoint.Endpoint}.
     *
     * @param context the {@link org.zeromq.ZMQ.Context} to use to acquire the socket
     * @return {@code ZMQ.Socket}
     */
    protected abstract ZMQ.Socket getSocket(ZMQ.Context context);

    @Override
    public void openInboundChannel() {
        if (!abstractChannelListener.isAlive())
            abstractChannelListener.start();
    }

    @Override
    public void closeInboundChannel() {
        if (abstractChannelListener.isAlive())
            abstractChannelListener.terminate();
    }

    @Override
    public void openOutboundChannel() {
        if (socket == null) {
            if (context == null)
                context = ZMQ.context(1);
            socket = getSocket(context);
            socket.bind(Endpoint.SCHEME + "*:" + port);
        }
    }

    @Override
    public void closeOutboundChannel() {
        if (socket != null) {
            socket.close();
            socket = null;
        }
        if (context != null) {
            context.term();
            context = null;
        }
    }

    @Override
    public boolean send(Message message) {
        if (socket == null)
            throw new IllegalStateException("Outbound channel not open!");
        if (message.getOrigin() == null)
            message.setOrigin(id);
        return socket.send(message.getPrefixedByteArray(), ZMQ.NOBLOCK);
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getId() {
        return id;
    }

}
