package com.spring.chat.chat;

import lombok.*;

/*
* This class is sent and received with JSON as the payload of STOMP messages
* (Spring's message converter (Jackson) converts JSON to POJO).
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;
}
