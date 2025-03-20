package song.teamo3.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import song.teamo3.domain.chat.dto.ChatDto;
import song.teamo3.domain.chat.dto.MessageDto;
import song.teamo3.domain.chat.service.ChatService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat/{chatRoomId}")
    @SendTo("/topic/chat/{chatRoomId}")
    public ResponseEntity<ChatDto> sendChat(@DestinationVariable("chatRoomId") Long chatRoomId,
                                            Message<MessageDto> message,
                                            Principal principal) {
        UserDetailsImpl userDetails = getUserDetails(principal);

        ChatDto chat = chatService.saveChat(userDetails.getUser(), chatRoomId, message.getPayload().getContent());

        return ResponseEntity.ok(chat);
    }

    private UserDetailsImpl getUserDetails(Principal principal) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) principal;

        return (UserDetailsImpl) authentication.getPrincipal();
    }
}
