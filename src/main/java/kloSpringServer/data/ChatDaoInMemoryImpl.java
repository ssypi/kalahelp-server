package kloSpringServer.data;

import kloSpringServer.model.ChatMessage;
import kloSpringServer.model.ChatRequest;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ChatDaoInMemoryImpl implements ChatDao {
    private final ConcurrentHashMap<Integer, List<ChatMessage>> chats = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, ChatRequest> chatRequests = new ConcurrentHashMap<>();

    @Override
    public ChatRequest requestChat(String nickname) {
        ChatRequest request = new ChatRequest(nickname);
        chatRequests.put(request.getId(), request);
        return request;
    }

    @Override
    @Nullable
    public ChatRequest acceptChat(int chatId) {
        ChatRequest request = chatRequests.remove(chatId);
        if (request != null) {
            chats.put(request.getId(), new ArrayList<ChatMessage>());
        }
        return request;
    }

    @Override
    public List<ChatRequest> getChatRequests() {
        return new ArrayList<>(chatRequests.values());
    }

    @Override
    public boolean addMessage(int chatId, ChatMessage message) {
        if (!chats.containsKey(chatId)) {
            if (acceptChat(chatId) == null) {
                return false;
            }
        }

        List<ChatMessage> messageList = chats.get(chatId);
        assert messageList != null;
        message.setIndex(messageList.size());
        messageList.add(message);
        return true;
    }

    @Override
    public List<ChatMessage> getMessagesForChat(int chatId, int messageIndex) {
        List<ChatMessage> messageList = chats.get(chatId);
        if (messageList == null) {
            return new ArrayList<>();
        }

        if (messageIndex > 0) {
            if (messageIndex > messageList.size()) {
                messageIndex = messageList.size();
            }
            return messageList.subList(messageIndex, messageList.size());
        } else {
            return messageList;
        }
    }

    /**
     * <p>Deletes the chat for the specified ID</P>
     *
     * @param chatId id of the chat to close.
     * @return {@code true} if chat was closed.<br>
     *         {@code false} if no chat exists for the specified id.
     */
    @Override
    public boolean closeChat(int chatId) {
        if (chats.containsKey(chatId)) {
            chats.remove(chatId);
            return true;
        } else {
            return false;
        }
    }
}
