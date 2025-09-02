package com.spring.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // It is used to activate the WebSocket-based messaging system with the STOMP protocol.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Here, the endpoint to which the client will establish a WebSocket connection is defined.
    // If you do not override this method, the client will not be able to connect at all → the system will not work.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    // Here, the prefixes to which the messages will be directed are determined.
    // Without these methods, publish/subscribe will not work.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Messages coming from the client go to addresses starting with this prefix and fall into @MessageMapping methods.
        registry.setApplicationDestinationPrefixes("/app");

        // Channels through which the server will broadcast to clients (e.g. /topic/messages)
        registry.enableSimpleBroker("/topic");
    }

}
