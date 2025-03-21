package song.teamo3.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import song.teamo3.domain.chat.service.ChatRoomService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    private final ChatRoomService chatRoomService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null && StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    String destination = accessor.getDestination();

                    if (destination != null && destination.startsWith("/topic/chat/")) {
                        Long roomId = getRoomId(destination);

                        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) accessor.getUser();
                        if (authentication == null) {
                            throw new IllegalArgumentException("사용자가 인증되지 않았습니다.");
                        }
                        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                        if (!chatRoomService.isMember(userDetails.getUser(), roomId)) {
                            throw new IllegalArgumentException("접근할 수 없습니다.");
                        }
                    }
                }

                return message;
            }
        });
    }

    private Long getRoomId(String destination) {
        String[] parts = destination.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }
}
