package com.spring.chat.config;

import com.spring.chat.chat.ChatMessage;
import com.spring.chat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    /*The service used to send messages to any destination from the server.
        With convertAndSend(destination, payload) you send a message
        directly to the broker.*/
    private final SimpMessageSendingOperations messageTemplate;

    /*
    * SessionDisconnectEvent => Event published by Spring when the WebSocket/STOMP session is closed (client disconnect).
    * */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        /*
        * It allows you to access the message headers within the event.
        * These headers also contain session attributes
        */
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        /*
        * We read the username you previously set as the session attribute
        * in the controller (addUser).
        * If it exists, we create a LEAVE ChatMessage and send it to
        * /topic/public with messageTemplate.convertAndSend.
        * This way, all other subscribers will receive the "x user left" message.
        */
        String username = (String)headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            log.info("User disconnected: {}",username);
            var chatMessage = ChatMessage.builder()
                    .messageType(MessageType.LEAVE)
                    .sender(username)
                    .build();

            messageTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }
}
