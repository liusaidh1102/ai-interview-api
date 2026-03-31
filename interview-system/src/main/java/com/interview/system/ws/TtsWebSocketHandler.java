package com.interview.system.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class TtsWebSocketHandler extends TextWebSocketHandler {
    private static final Map<String, Set<WebSocketSession>> sessions = new ConcurrentHashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = (String) session.getAttributes().get("userId");
        sessions.computeIfAbsent(userId, k -> Collections.newSetFromMap(new ConcurrentHashMap<>())).add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = (String) session.getAttributes().get("userId");
        Set<WebSocketSession> set = sessions.getOrDefault(userId, Collections.emptySet());
        set.remove(session);
    }

    public static void push(String userId, Object payload) {
        Set<WebSocketSession> set = sessions.get(userId);
        if (set == null) return;
        for (WebSocketSession s : set) {
            try {
                s.sendMessage(new org.springframework.web.socket.TextMessage(mapper.writeValueAsString(payload)));
            } catch (Exception ignored) {
            }
        }
    }
}
