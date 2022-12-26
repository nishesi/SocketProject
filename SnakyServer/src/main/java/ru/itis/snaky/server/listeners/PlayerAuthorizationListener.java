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

        if (!validateNickname(connection, params.getNickname())) {
            connection.getOutputStream().send(new Message<>(MessageType.AUTHORIZATION, new AuthenticationParams(params.getNickname(), false)));
        } else {
            connection.setPlayerNickname(params.getNickname());
            connection.getOutputStream().send(new Message<>(MessageType.AUTHORIZATION, new AuthenticationParams(params.getNickname(), true)));
        }
    }

    private boolean validateNickname(Connection connection, String nickname) {

        for (Connection player : this.server.getConnections()) {
            if (!connection.getUuid().equals(player.getUuid())) {
                if (nickname.equals(player.getPlayerNickname())) {
                    return false;
                }
            }
        }

        return true;
    }
}
