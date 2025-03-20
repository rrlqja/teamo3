package song.teamo3.domain.common.exception.chat.exceptions;

import song.teamo3.domain.common.exception.chat.ChatException;

public class ChatRoomNotFoundException extends ChatException {
    public ChatRoomNotFoundException() {
        super("ChatRoom Not Found Exception");
    }

    public ChatRoomNotFoundException(String message) {
        super(message);
    }
}
