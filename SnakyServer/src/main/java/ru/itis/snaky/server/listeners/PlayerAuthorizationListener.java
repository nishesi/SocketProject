package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.AuthenticationParams;
import ru.itis.snaky.server.core.Connection;

public class PlayerAuthorizationListener extends AbstractServerEventListener {

    public PlayerAuthorizationListener() {
        super(MessageType.AUTHORIZATION.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {

        AuthenticationParams params = (AuthenticationParams) message.getParams();

        if (!validateNickname(params.getNickname())) {
            connection.getOutputStream().send(new Message<>(MessageType.AUTHORIZATION, new AuthenticationParams(params.getNickname(), false)));
        } else {
            connection.setPlayerNickname(params.getNickname());
            connection.getOutputStream().send(new Message<>(MessageType.AUTHORIZATION, new AuthenticationParams(params.getNickname(), true)));
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
