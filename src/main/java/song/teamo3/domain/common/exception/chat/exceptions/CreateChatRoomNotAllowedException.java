package song.teamo3.domain.common.exception.chat.exceptions;

import song.teamo3.domain.common.exception.chat.ChatException;

public class CreateChatRoomNotAllowedException extends ChatException {
    public CreateChatRoomNotAllowedException() {
        super("Create ChatRoom Not Allowed Exception");
    }

    public CreateChatRoomNotAllowedException(String message) {
        super(message);
    }
}
