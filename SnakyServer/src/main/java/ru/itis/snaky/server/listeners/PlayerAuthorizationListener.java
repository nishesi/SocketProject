package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.threads.OutputStreamThread;
import ru.itis.snaky.server.core.Connection;

public class PlayerAuthorizationListener extends AbstractServerEventListener {
    public PlayerAuthorizationListener() {
        super(MessageType.AUTHORIZATION.getValue());
    }

    @Override
    public void handle(Connection connection, Message message) {
        if (!validateNickname((String) message.getParameter(0))) {
            connection.getOutputStream().send(new Message(MessageType.AUTHORIZATION, new Object[]{"0"}));
        } else {
            connection.setPlayerNickname((String) message.getParameter(0));
            connection.getOutputStream().send(new Message(MessageType.AUTHORIZATION, new Object[]{"1"}));
        }
    }

    private boolean validateNickname(String nickname) {

        for (Connection connection : this.server.getConnections()) {
            if (nickname.equals(connection.getPlayerNickname())) {
                return false;
            }
        }

        return true;
    }
}
