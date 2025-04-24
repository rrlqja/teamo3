package song.teamo3.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo3.domain.chat.dto.ChatPageDto;
import song.teamo3.domain.chat.dto.ChatRoomDto;
import song.teamo3.domain.chat.dto.ChatRoomPageDto;
import song.teamo3.domain.chat.dto.ModifyChatRoomTitleDto;
import song.teamo3.domain.chat.service.ChatRoomService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/chatRoomList")
    public String getChattingRoomList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PageableDefault(size = 10, page = 0) Pageable pageable,
                                      Model model) {
        Page<ChatRoomPageDto> chatRoomPage = chatRoomService.getChatRoomPage(userDetails.getUser(), pageable);

        model.addAttribute("chatRoomPage", chatRoomPage);

        return "chatroom/chatRoomList";
    }

    @GetMapping("/api/chatRoomList")
    public ResponseEntity<Page<ChatRoomPageDto>> getChatRoomApi(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<ChatRoomPageDto> chatRoomPage = chatRoomService.getChatRoomPage(userDetails.getUser(), pageable);

        return ResponseEntity.ok(chatRoomPage);
    }

    @GetMapping("/{chatRoomId}/chatList")
    public ResponseEntity<ChatRoomDto> getChatList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable("chatRoomId") Long chatRoomId,
                                                   @PageableDefault(size = 10, page = 0) Pageable pageable) {
        ChatRoomDto chatRoom = chatRoomService.getChatList(userDetails.getUser(), chatRoomId, pageable);

        return ResponseEntity.ok(chatRoom);
    }

    @PostMapping("/modifyTitle/{chatRoomId}")
    public ResponseEntity<?> postModifyChatRoomTitle(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable("chatRoomId") Long chatRoomId,
                                                     @RequestBody ModifyChatRoomTitleDto modifyChatRoomDto) {
        chatRoomService.modify(userDetails.getUser(), chatRoomId, modifyChatRoomDto);

        return ResponseEntity.ok().build();
    }
}
