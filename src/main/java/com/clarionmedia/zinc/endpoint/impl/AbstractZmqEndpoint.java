package com.clarionmedia.zinc.endpoint.impl;

import com.clarionmedia.zinc.endpoint.Endpoint;
import com.clarionmedia.zinc.endpoint.MessageHandlerFactory;
import com.clarionmedia.zinc.message.Message;
import org.zeromq.ZMQ;

import java.util.UUID;

/**
 * Abstract implementation of {@link Endpoint} containing common code for ZeroMQ implementations.
 */
public abstract class AbstractZmqEndpoint implements Endpoint {

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
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use
     */
    public AbstractZmqEndpoint(String hostAddress, int hostPort, int port, int type, MessageHandlerFactory
            messageHandlerFactory) {
        this.abstractChannelListener = new ZmqChannelListener(this, hostAddress, hostPort, type, messageHandlerFactory);
        this.port = port;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Creates a new {@code AbstractZmqEndpoint} instance.
     *
     * @param type                  the ZMQ socket type
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use
     */
    public AbstractZmqEndpoint(int port, int type, MessageHandlerFactory messageHandlerFactory) {
        this.abstractChannelListener = new ZmqChannelListener(this, type, messageHandlerFactory);
        this.port = port;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Creates a {@link org.zeromq.ZMQ.Socket} for use by the {@link Endpoint}.
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
