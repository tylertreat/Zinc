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
 * A {@code MessageHandlerFactory} is used to build new {@link MessageHandler} instances to be used by
 * a {@link ChannelListener} so that it can be used to handle incoming messages.
 */
public interface MessageHandlerFactory {

    /**
     * Constructs a new {@link MessageHandler} for the given message.
     *
     * @param endpoint    the {@link Endpoint} that received the message
     * @param messageData the message data to handle
     */
    MessageHandler build(Endpoint endpoint, byte[] messageData);

}