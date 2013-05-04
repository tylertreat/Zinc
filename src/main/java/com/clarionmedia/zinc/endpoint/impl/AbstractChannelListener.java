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

package com.clarionmedia.zinc.endpoint.impl;

import com.clarionmedia.zinc.endpoint.ChannelListener;
import com.clarionmedia.zinc.endpoint.Endpoint;
import com.clarionmedia.zinc.endpoint.MessageHandlerFactory;
import com.clarionmedia.zinc.endpoint.Peer;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract implementation of {@link ChannelListener}.
 */
public abstract class AbstractChannelListener extends Thread implements ChannelListener {

    protected final Endpoint endpoint;
    protected final ExecutorService threadPool;
    protected final MessageHandlerFactory messageHandlerFactory;
    protected Set<Peer> peers;
    protected volatile boolean terminate;

    /**
     * Creates a new {@code AbstractChannelListener} instance.
     *
     * @param endpoint              the {@link Endpoint} this {@code AbstractChannelListener} belongs to
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use to construct
     *                              {@link com.clarionmedia.zinc.endpoint.MessageHandler} instances
     */
    public AbstractChannelListener(Endpoint endpoint, MessageHandlerFactory messageHandlerFactory) {
        this(endpoint, 1, messageHandlerFactory);
    }

    /**
     * Creates a new {@code AbstractChannelListener} instance.
     *
     * @param endpoint              the {@link Endpoint} this {@code AbstractChannelListener} belongs to
     * @param threadMultiplier      the multiplier used to allocate the thread pool size, which is equal to
     *                              {@code number of CPUs * threadMultiplier}.
     * @param messageHandlerFactory the {@link MessageHandlerFactory} to use to construct
     *                              {@link com.clarionmedia.zinc.endpoint.MessageHandler} instances
     */
    public AbstractChannelListener(Endpoint endpoint, int threadMultiplier, MessageHandlerFactory messageHandlerFactory) {
        this.endpoint = endpoint;
        this.messageHandlerFactory = messageHandlerFactory;
        // Message handling operations are probably CPU-bound, so allocate 1 thread per core by default
        this.threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * threadMultiplier);
        this.peers = new HashSet<Peer>();
    }

    @Override
    public final void run() {
        listen();
    }

    @Override
    public final void terminate() {
        terminate = true;
    }

}