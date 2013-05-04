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

package com.clarionmedia.zinc.message.impl;

/**
 * Implementation of {@link AbstractMessage} representing a partial fragment of a {@link Document}.
 */
public abstract class DocumentFragment<T> extends AbstractMessage {

    public DocumentFragment() {
        super(MessageType.DOCUMENT_FRAGMENT);
    }

    @Override
    public MessageType getType() {
        return MessageType.DOCUMENT_FRAGMENT;
    }

}