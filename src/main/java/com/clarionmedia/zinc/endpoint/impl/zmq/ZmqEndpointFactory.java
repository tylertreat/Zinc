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

import com.clarionmedia.zinc.endpoint.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Implementation of {@link EndpointFactory} using ZeroMQ-based {@code Endpoint} implementations.
 */
public class ZmqEndpointFactory implements EndpointFactory {

    @Override
    public Endpoint buildClientEndpoint(String hostAddress, int hostDiscoveryPort, String token,
                                        MessageHandlerFactory messageHandlerFactory) {
        int hostSharePort = 0;
        int port = 0;
        Socket socket = null;
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;
        try {
            System.out.println("Requesting a connection to " + hostAddress);
            socket = new Socket(InetAddress.getByName(hostAddress), hostDiscoveryPort);
            port = socket.getLocalPort();

            // Send the authentication token
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(token);
            outputStream.flush();

            // Check the response
            String response = inputStream.readUTF();
            if (response.startsWith("0")) {
                // 0 indicates authentication failed
                System.out.println("Request failed authentication");
                return null;
            }

            // Otherwise, authentication succeeded, so we can get the host's share port and connect
            hostSharePort = Integer.valueOf(response.substring(response.indexOf(" ") + 1));
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (socket != null)
                    socket.close();
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ZmqClientEndpoint(hostAddress, hostSharePort, port, messageHandlerFactory);
    }

    @Override
    public HostEndpoint buildHostEndpoint(int port, MessageHandlerFactory messageHandlerFactory) {
        return new ZmqHostEndpoint(port, messageHandlerFactory);
    }

    @Override
    public HostEndpoint buildHostEndpoint(int port, MessageHandlerFactory messageHandlerFactory,
                                          Authenticator
                                                  authenticator) {
        return new ZmqHostEndpoint(port, messageHandlerFactory, authenticator);
    }
}
