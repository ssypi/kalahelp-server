package kloSpringServer.data;

import kloSpringServer.model.ChatMessage;
import kloSpringServer.model.ChatRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.Date;
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
    public ChatRequest requestChat() {
        ChatRequest request = new ChatRequest();
        request.setId(ChatRequest.generateId());
        request.setDate(new Date());
        request.setNickname("kalamies");
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
}
