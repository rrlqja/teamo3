package song.teamo3.domain.common.exception.chat.exceptions;

import song.teamo3.domain.common.exception.chat.ChatException;

public class ChatRoomUserNotFoundException extends ChatException {
    public ChatRoomUserNotFoundException() {
        super("Chat Room User Not Found Exception");
    }

    public ChatRoomUserNotFoundException(String message) {
        super(message);
    }
}
