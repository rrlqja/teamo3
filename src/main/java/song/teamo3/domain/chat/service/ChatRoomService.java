package song.teamo3.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.chat.dto.ChatPageDto;
import song.teamo3.domain.chat.dto.ChatRoomDto;
import song.teamo3.domain.chat.dto.ChatRoomListDto;
import song.teamo3.domain.chat.dto.ChatRoomPageDto;
import song.teamo3.domain.chat.dto.ChatRoomUserListDto;
import song.teamo3.domain.chat.dto.ModifyChatRoomTitleDto;
import song.teamo3.domain.chat.entity.ChatRoom;
import song.teamo3.domain.chat.entity.ChatRoomUser;
import song.teamo3.domain.chat.repository.ChatJpaRepository;
import song.teamo3.domain.chat.repository.ChatRoomJpaRepository;
import song.teamo3.domain.chat.repository.ChatRoomUserJpaRepository;
import song.teamo3.domain.common.exception.chat.exceptions.AlreadyExistsChatRoomException;
import song.teamo3.domain.common.exception.chat.exceptions.ChatRoomNotFoundException;
import song.teamo3.domain.common.exception.chat.exceptions.ChatRoomUserNotFoundException;
import song.teamo3.domain.common.exception.chat.exceptions.ModifyChatRoomNotAllowedException;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studymember.entity.StudyMember;
import song.teamo3.domain.studymember.repository.StudyMemberJpaRepository;
import song.teamo3.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatJpaRepository chatRepository;
    private final ChatRoomUserJpaRepository chatRoomUserRepository;
    private final ChatRoomJpaRepository chatRoomRepository;
    private final StudyMemberJpaRepository studyMemberRepository;

    @Transactional
    public Long createChatRoom(Study study) {
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findChatRoomByStudy(study);
        if (optionalChatRoom.isPresent()) {
            throw new AlreadyExistsChatRoomException("이미 채팅방이 존재합니다.");
        }

        ChatRoom chatRoom = ChatRoom.create(study);
        ChatRoom createChatRoom = chatRoomRepository.save(chatRoom);

        log.info("[Create ChatRoom] id: {}", createChatRoom.getId());

        List<StudyMember> studyMemberList = studyMemberRepository.findStudyMembersByStudy(study);
        for (StudyMember studyMember : studyMemberList) {
            addChatRoomUser(createChatRoom.getStudy(), studyMember.getUser());
        }

        return createChatRoom.getId();
    }

    @Transactional
    public Long addChatRoomUser(Study study, User addUser) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByStudy(study)
                .orElseThrow(ChatRoomNotFoundException::new);

        ChatRoomUser chatRoomUser = ChatRoomUser.create(chatRoom, addUser);
        ChatRoomUser saveChatRoomUser = chatRoomUserRepository.save(chatRoomUser);

        log.info("[Save ChatRoomUser] id: {}, userId: {}", saveChatRoomUser.getId(), chatRoomUser.getUser().getId());

        return saveChatRoomUser.getId();
    }

    @Transactional
    public Page<ChatRoomPageDto> getChatRoomPage(User user, Pageable pageable) {
        return chatRoomUserRepository.findChatRoomUsersByUser(user, pageable)
                .map(ChatRoomUser::getChatRoom)
                .map(cr -> new ChatRoomPageDto(cr,
                        chatRoomUserRepository.findChatRoomUsersByChatRoom(cr).stream().map(ChatRoomUserListDto::new).toList(),
                        chatRepository.findLastChatByChatRoom(cr).orElse(null)));
    }

    @Transactional
    public ChatRoomDto getChatList(User user, Long chatRoomId, Pageable pageable) {
        ChatRoom chatRoom = getChatRoom(chatRoomId);

        chatRoomUserRepository.findChatRoomUserByChatRoomAndUser(chatRoom, user)
                .orElseThrow(()-> new ChatRoomUserNotFoundException("가입되지 않은 채팅방입니다."));

        Page<ChatPageDto> chatPage = chatRepository.findChatsByChatRoom(chatRoom, pageable)
                .map(ChatPageDto::new);

        return new ChatRoomDto(chatRoom, user, chatPage);
    }

    @Transactional
    public void exitChatRoom(User user, Study study) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByStudy(study)
                .orElseThrow(ChatRoomNotFoundException::new);

        ChatRoomUser chatRoomUser = chatRoomUserRepository.findChatRoomUserByChatRoomAndUser(chatRoom, user)
                .orElseThrow(ChatRoomUserNotFoundException::new);

        chatRoomUser.delete();
        log.info("[exit chatroom] id: {}", chatRoomUser.getId());
    }

    @Transactional
    public boolean isMember(User user, Long roomId) {
        ChatRoom chatRoom = getChatRoom(roomId);

        return chatRoomUserRepository.findChatRoomUserByChatRoomAndUser(chatRoom, user)
                .isPresent();
    }

    @Transactional
    public Page<ChatRoomListDto> getChatRoomList(User user) {
        return chatRoomUserRepository.findChatRoomUsersByUser(user, PageRequest.of(0, 4))
                .map(ChatRoomUser::getChatRoom)
                .map(chatRoom -> new ChatRoomListDto(chatRoom, chatRepository.findLastChatByChatRoom(chatRoom).orElse(null)));
    }

    @Transactional
    public void modify(User user, Long chatRoomId, ModifyChatRoomTitleDto modifyChatRoomDto) {
        ChatRoom chatRoom = getChatRoom(chatRoomId);

        if (!chatRoom.getStudy().getWriter().getId().equals(user.getId())) {
            throw new ModifyChatRoomNotAllowedException("권한이 없습니다.");
        }

        chatRoom.modify(modifyChatRoomDto.getTitle());

//        chatRoomRepository.save(chatRoom);
    }

    private ChatRoom getChatRoom(Long roomId) {
        return chatRoomRepository.findChatRoomById(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }
}
