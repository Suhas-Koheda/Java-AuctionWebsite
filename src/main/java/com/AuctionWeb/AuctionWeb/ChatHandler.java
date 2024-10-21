package com.AuctionWeb.AuctionWeb;


import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ChatHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<String, WebSocketSession> room = rooms.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        room.put(session.getId(), session);
        broadcast(session, "New user joined the room: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<String, WebSocketSession> room = rooms.get(roomId);
        if (room != null) {
            room.remove(session.getId());
            if (room.isEmpty()) {
                rooms.remove(roomId);
            }
            broadcast(session, "User left the room: " + session.getId());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        broadcast(session, message.getPayload());
    }

    private void broadcast(WebSocketSession session, String message) throws IOException {
        String roomId = getRoomId(session);
        ConcurrentHashMap<String, WebSocketSession> room = rooms.get(roomId);
        if (room != null) {
            for (WebSocketSession s : room.values()) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(message));
                }
            }
        }
    }

    private String getRoomId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String roomId = null;

        // Parse the URI to extract the roomId from the query parameters
        if (uri != null && uri.contains("roomId=")) {
            String[] params = uri.split("\\?");
            if (params.length > 1) {
                String[] queryParams = params[1].split("&");
                for (String param : queryParams) {
                    if (param.startsWith("roomId=")) {
                        roomId = param.substring("roomId=".length());
                        break;
                    }
                }
            }
        }

        return (roomId != null) ? roomId : "defaultRoom";
    }
}
