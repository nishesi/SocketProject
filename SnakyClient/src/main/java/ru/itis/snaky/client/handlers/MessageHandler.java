package ru.itis.snaky.client.handlers;

import ru.itis.snaky.protocol.message.parameters.MessageParams;

public interface MessageHandler<T extends MessageParams> {
    void handle(T params);
}
