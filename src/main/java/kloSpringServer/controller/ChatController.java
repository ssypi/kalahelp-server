package kloSpringServer.controller;

import kloSpringServer.data.ChatDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.ChatMessage;
import kloSpringServer.model.ChatRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/chat")
public class ChatController {
    private static final Logger logger = Logger.getLogger(ChatController.class);
//    private final Map<DeferredResult<ApiResult>, Integer> chatRequests = new ConcurrentHashMap<>();
    private final Map<DeferredResult<List<ChatMessage>>, Integer> chatRequests = new ConcurrentHashMap<>();

    @Autowired
    ChatDao chatDao;

    private void complete(ChatMessage message, int chatId) {
        List<ChatMessage> messageList = new ArrayList<>();
        messageList.add(message);

        for (Map.Entry<DeferredResult<List<ChatMessage>>, Integer> entry : this.chatRequests.entrySet()) {
            if (entry.getValue() == chatId) {
                entry.getKey().setResult(messageList);
            }
        }
    }

    @RequestMapping(value = "/{chatId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody ApiResult addMessage(@RequestBody ChatMessage message, @PathVariable int chatId) {
        chatDao.addMessage(chatId, message);
        if (chatRequests.containsValue(chatId)) {
            complete(message, chatId);
        }
        return new ApiResult();
    }

    @RequestMapping(value = "/{chatId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    DeferredResult<? extends List<ChatMessage>> getChatMessages(@PathVariable int chatId, @RequestParam(value = "index", defaultValue = "0") int messageIndex) {
        final DeferredResult<List<ChatMessage>> deferredResult = new DeferredResult<>(30000L, Collections.emptyList());
//        final DeferredResult<ApiResult> deferredResult = new DeferredResult<>(30000L, Collections.emptyList());
        this.chatRequests.put(deferredResult, chatId);

        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                chatRequests.remove(deferredResult);
            }
        });

        List<ChatMessage> messageList = chatDao.getMessagesForChat(chatId, messageIndex);
        if (messageList != null && !messageList.isEmpty()) {
            deferredResult.setResult(messageList);
        }
        return deferredResult;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ApiResult<List<ChatRequest>> getWaitingChatRequests() {
        return new ApiResult<>(chatDao.getChatRequests(), ApiResult.STATUS_OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> requestChat(@RequestBody ChatRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;

        ChatRequest response = chatDao.requestChat(chatRequest.getNickname());
        ApiResult<ChatRequest> result;

        if (response != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/chat/" + response.getId()).build().toUri();
            headers.setLocation(uri);
            status = HttpStatus.CREATED;
            result = new ApiResult<>(response, ApiResult.STATUS_OK);
        } else {
            status = HttpStatus.BAD_REQUEST;
            result = new ApiResult<>(ApiResult.STATUS_ERROR);
        }
        return new ResponseEntity<>(result, headers, status);
    }

}
