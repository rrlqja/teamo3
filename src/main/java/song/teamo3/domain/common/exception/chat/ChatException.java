package song.teamo3.domain.common.exception.chat;

import song.teamo3.domain.common.exception.TeamoException;

public class ChatException extends TeamoException {
    public ChatException() {
        super("Chat Exception");
    }

    public ChatException(String message) {
        super(message);
    }
}
