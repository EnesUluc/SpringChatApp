package com.spring.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    /* @MessageMapping :
    *   When the destination of the incoming STOMP message is
    *   /app/chat.sendMessage, it falls into this method.
    *   (Why /app? Because we specified setApplicationDestinationPrefixes("/app")
    *   in WebSocketConfig.)
    */

    /* @SendTo
    * Send the ChatMessage object returned by the method to /topic/public via the broker.
    * (It reaches the subscribers there).
    *
    *Why use @SendTo?
    * Because the returned object will automatically be sent to the
    *  specified topic—it keeps the code short.
    *  Alternatively, you can use SimpMessagingTemplate.convertAndSend("/topic/public", chatMessage)
    *  within the method.
     */

    /* @Payload => If the message body is JSON, Spring converts it to a ChatMessage object*/

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        // Ad username in websocket session
        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        return chatMessage;
    }
}
