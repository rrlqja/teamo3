package song.teamo3.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.chat.dto.ChatDto;
import song.teamo3.domain.chat.entity.Chat;
import song.teamo3.domain.chat.entity.ChatRoom;
import song.teamo3.domain.chat.entity.ChatRoomUser;
import song.teamo3.domain.chat.repository.ChatJpaRepository;
import song.teamo3.domain.chat.repository.ChatRoomJpaRepository;
import song.teamo3.domain.chat.repository.ChatRoomUserJpaRepository;
import song.teamo3.domain.common.exception.chat.exceptions.ChatRoomNotFoundException;
import song.teamo3.domain.common.exception.chat.exceptions.ChatRoomUserNotFoundException;
import song.teamo3.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomUserJpaRepository chatRoomUserRepository;
    private final ChatRoomJpaRepository chatRoomRepository;
    private final ChatJpaRepository chatRepository;

    @Transactional
    public ChatDto saveChat(User user, Long chatRoomId, String message) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);

        ChatRoomUser chatRoomUser = chatRoomUserRepository.findChatRoomUserByChatRoomAndUser(chatRoom, user)
                .orElseThrow(ChatRoomUserNotFoundException::new);

        Chat chat = Chat.create(user, chatRoom, message);
        Chat saveChat = chatRepository.save(chat);

        chatRoom.updateLastChatTime();

        return new ChatDto(saveChat);
    }
}
