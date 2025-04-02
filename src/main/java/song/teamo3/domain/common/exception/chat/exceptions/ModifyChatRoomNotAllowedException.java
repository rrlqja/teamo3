package song.teamo3.domain.common.exception.chat.exceptions;

import song.teamo3.domain.common.exception.chat.ChatException;

public class ModifyChatRoomNotAllowedException extends ChatException {
    public ModifyChatRoomNotAllowedException() {
        super("Modify ChatRoom Not Allowed Exception");
    }

    public ModifyChatRoomNotAllowedException(String message) {
        super(message);
    }
}
