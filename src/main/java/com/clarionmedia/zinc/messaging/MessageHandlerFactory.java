package com.clarionmedia.zinc.messaging;

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