package kloSpringServer.data;

import kloSpringServer.model.ChatMessage;
import kloSpringServer.model.ChatRequest;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface ChatDao {
    ChatRequest requestChat(String nickname);
    List<ChatRequest> getChatRequests();
    ChatRequest acceptChat(int chatId);

    void addMessage(int chatId,ChatMessage message);

    List<ChatMessage> getMessagesForChat(int chatId, int messageIndex);

    boolean closeChat(int chatId);
}
