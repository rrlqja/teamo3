package song.teamo3.domain.common.exception.chat.exceptions;

import song.teamo3.domain.common.exception.chat.ChatException;

public class AlreadyExistsChatRoomException extends ChatException {
    public AlreadyExistsChatRoomException() {
        super("Already Exists ChatRoom Exception");
    }

    public AlreadyExistsChatRoomException(String message) {
        super(message);
    }
}
