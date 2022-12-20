package ru.itis.snaky.client;

import ru.itis.snaky.protocol.message.Message;

public interface MessageHandler {
    void handle(Message message);
}
