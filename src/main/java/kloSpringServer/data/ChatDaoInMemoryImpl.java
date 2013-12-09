package kloSpringServer.data;

import kloSpringServer.model.ChatMessage;
import kloSpringServer.model.ChatRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 6.11.2013
 * Time: 22:58
 */
@Repository
public class ChatDaoInMemoryImpl implements ChatDao {
    private static final ConcurrentHashMap<Integer, List<ChatMessage>> chats = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, ChatRequest> chatRequests = new ConcurrentHashMap<>();

    @Override
    public ChatRequest requestChat(String nickname) {
        ChatRequest request = new ChatRequest(nickname);
        chatRequests.put(request.getId(), request);
        return request;
    }

    @Override
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
    public void addMessage(int chatId, ChatMessage message) {
        List<ChatMessage> messageList = chats.get(chatId);
        if (messageList == null) {
            if (acceptChat(chatId) != null) {
                messageList = chats.get(chatId);
            } else {
                return;
            }
        }
        message.setIndex(messageList.size());
        messageList.add(message);
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
     * @param chatId id of the chat to close.
     * @return {@code true} if chat was closed.<br>
     *     {@code false} if no chat exists for the specified id.
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
