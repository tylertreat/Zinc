package com.clarionmedia.zinc.endpoint.impl;

import com.clarionmedia.zinc.endpoint.Authenticator;
import com.clarionmedia.zinc.endpoint.ClientAddedListener;
import com.clarionmedia.zinc.endpoint.HostEndpoint;
import com.clarionmedia.zinc.endpoint.MessageHandlerFactory;
import com.clarionmedia.zinc.util.ThreadUtils;
import org.zeromq.ZMQ;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementation of {@link HostEndpoint} which represents the notion of a "host" in a distributed client-host cluster
 * application. This implementation relies on ZeroMQ for communication.
 */
public class ZmqHostEndpoint extends AbstractZmqEndpoint implements HostEndpoint {

    private ConnectionManager connectionManager;
    private Authenticator authenticator;
    private List<ClientAddedListener> clientAddedListeners;
    private final int discoveryPort;

    /**
     * Creates a new {@code ZmqHostEndpoint} instance.
     *
     * @param port                  the port to bind to
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use for handling incoming messages
     */
    public ZmqHostEndpoint(int port, MessageHandlerFactory messageHandlerFactory) {
        super(port, ZMQ.PULL, messageHandlerFactory);
        discoveryPort = 0;
        connectionManager = new ConnectionManager();
        clientAddedListeners = new ArrayList<ClientAddedListener>();
    }

    /**
     * Creates a new {@code ZmqHostEndpoint} instance.
     *
     * @param port                  the port to bind to
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use for handling incoming messages
     * @param authenticator         the {@link Authenticator} to use to authenticate connection requests
     */
    public ZmqHostEndpoint(int port, MessageHandlerFactory messageHandlerFactory, Authenticator authenticator) {
        this(port, messageHandlerFactory);
        this.authenticator = authenticator;
    }

    /**
     * Creates a new {@code ZmqHostEndpoint} instance.
     *
     * @param port                  the port to bind to
     * @param discoveryPort         the port to bind to for listening for connection requests
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use for handling incoming messages
     */
    public ZmqHostEndpoint(int port, int discoveryPort, MessageHandlerFactory messageHandlerFactory) {
        super(port, ZMQ.PULL, messageHandlerFactory);
        this.discoveryPort = discoveryPort;
    }

    /**
     * Creates a new {@code ZmqHostEndpoint} instance.
     *
     * @param port                  the port to bind to
     * @param discoveryPort         the port to bind to for listening for connection requests
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use for handling incoming messages
     * @param authenticator         the {@link Authenticator} to use to authenticate connection requests
     */
    public ZmqHostEndpoint(int port, int discoveryPort, MessageHandlerFactory messageHandlerFactory,
                           Authenticator authenticator) {
        super(port, ZMQ.PULL, messageHandlerFactory);
        this.discoveryPort = discoveryPort;
        connectionManager = new ConnectionManager();
        clientAddedListeners = new ArrayList<ClientAddedListener>();
        this.authenticator = authenticator;
    }

    @Override
    protected ZMQ.Socket getSocket(ZMQ.Context context) {
        return context.socket(ZMQ.PUB);
    }

    @Override
    public void addClient(String address, int port) {
        abstractChannelListener.addPeer(address, port);
        for (ClientAddedListener listener : clientAddedListeners) {
            listener.onClientAdded(address, port);
        }
    }

    @Override
    public int getConnectionRequestPort() {
        return connectionManager.getPort();
    }

    @Override
    public void addClientAddedListener(ClientAddedListener listener) {
        clientAddedListeners.add(listener);
    }

    @Override
    public void openInboundChannel() {
        if (!connectionManager.isAlive())
            connectionManager.start();
        super.openInboundChannel();
    }

    @Override
    public void closeInboundChannel() {
        if (connectionManager.isAlive())
            connectionManager.terminate();
        super.closeInboundChannel();
    }

    @Override
    public boolean isHost() {
        return true;
    }

    @Override
    public boolean requestFullDocument() {
        throw new UnsupportedOperationException();
    }

    private class ConnectionManager extends Thread {

        private ServerSocket connectionSocket;
        private ExecutorService connectionPool;
        private volatile boolean terminate;

        public ConnectionManager() {
            try {
                connectionSocket = new ServerSocket(discoveryPort);
            } catch (IOException e) {
                // TODO
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            connectionPool = Executors.newSingleThreadExecutor();
            while (!terminate) {
                try {
                    connectionPool.execute(new ConnectionHandler(connectionSocket.accept()));
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            }
            ThreadUtils.shutdownAndAwaitTermination(connectionPool);
        }

        public void terminate() {
            terminate = true;
        }

        public int getPort() {
            return connectionSocket.getLocalPort();
        }

        private class ConnectionHandler implements Runnable {

            private Socket connection;

            public ConnectionHandler(Socket connection) {
                this.connection = connection;
            }

            @Override
            public void run() {
                try {
                    // Get the address of the incoming connection
                    String address = connection.getInetAddress().getHostAddress();
                    // and the incoming port
                    int port = connection.getPort();
                    // Here is a stream that will be sent to the connect client
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    // NOTE: Right now, there is no authenticator...
                    //    so all of the clients are accepted.
                    if (authenticator == null) {
                        // Just add the peer (client) if there's no Authenticator
                        ZmqHostEndpoint.this.addClient(address, port);
                        // Indicator, to the client, that we connected well.
                        outputStream.writeUTF("1 " + ZmqHostEndpoint.this.port);
                        // make sure it really goes (down) get the plunger!
                        outputStream.flush();

                    } else {
                        // Otherwise authenticate the connection request
                        DataInputStream inputStream = new DataInputStream(connection.getInputStream());
                        String token = inputStream.readUTF(); // Blocking call
                        if (authenticator.isAuthenticated(address, port, token)) {
                            ZmqHostEndpoint.this.addClient(address, port);
                            outputStream.writeUTF("1 " + ZmqHostEndpoint.this.port);
                            outputStream.flush();
                        } else {
                            System.out.println("Connection request from " + address + ":" + port + " is not " +
                                    "authenticated");
                            outputStream.writeUTF("0 Authentication Failed");
                            outputStream.flush();
                        }
                        inputStream.close();
                    }
                    // close the stream, done writing for now.
                    outputStream.close();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (IOException e) {
                        // We're boned
                        e.printStackTrace();
                    }
                }
            }

        }

    }

}
