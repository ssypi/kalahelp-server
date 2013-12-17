package kloSpringServer.controller;

import kloSpringServer.data.ChatDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.ChatMessage;
import kloSpringServer.model.ChatRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/chat")
public class ChatController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(ChatController.class);
    private final Map<DeferredResult<ApiResult<List<ChatMessage>>>, Integer> waitingMessageRequests = new ConcurrentHashMap<>();

    @Autowired
    ChatDao chatDao;

    private void complete(ChatMessage message, int chatId) {
        List<ChatMessage> messageList = new ArrayList<>();
        messageList.add(message);
        ApiResult<List<ChatMessage>> result = new ApiResult<>(messageList);

        for (Map.Entry<DeferredResult<ApiResult<List<ChatMessage>>>, Integer> entry : this.waitingMessageRequests.entrySet()) {
            if (entry.getValue() == chatId) {
                entry.getKey().setResult(result);
            }
        }
    }

    @RequestMapping(value = "/{chatId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody ApiResult addMessage(@RequestBody ChatMessage message, @PathVariable int chatId) {
        if (!chatDao.addMessage(chatId, message)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                    "Chat with id " + chatId + " doesn't exist.");
        } else {
            if (waitingMessageRequests.containsValue(chatId)) {
                complete(message, chatId);
            }
            return new ApiResult(HttpStatus.OK.value(), "Message sent.");
        }
    }

    /**
     * <p>Gets either all or only particular chat messages for the specified chat id.</p>
     * <p>Example usage: /chat/295317?index=5 will wait up to 30 seconds until someone posts
     * a 6th message to the chat with id 295317.</p>
     *
     * <p><b>NOTE: Uses long polling:</b> response will be delayed up to 30 seconds to wait for
     * new messages for the specified id and index.</p>
     *
     * @param chatId id of the chat is specified in the request url.
     * @param messageIndex only get messages starting from this index. Index starts from 0.
     *                     If no index is specified or the index is 0, will return all messages.
     *                     Index is given as a request param ?index=4
     * @return a list of {@link ChatMessage} objects as json. <br>
     *     If the connection times out, will return an empty list.<br>
     *     TODO: If there is no chat for the specified ID, will return a 404
     */
    @RequestMapping(value = "/{chatId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    DeferredResult<? extends ApiResult<List<ChatMessage>>> getChatMessages(@PathVariable int chatId, @RequestParam(value = "index", defaultValue = "0") int messageIndex) {
        ApiResult timeOutResult = new ApiResult(408, "Request timed out.");
        final DeferredResult<ApiResult<List<ChatMessage>>> deferredResult = new DeferredResult<>(30000L, timeOutResult);
        this.waitingMessageRequests.put(deferredResult, chatId);

        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                waitingMessageRequests.remove(deferredResult);
            }
        });

        List<ChatMessage> messageList = chatDao.getMessagesForChat(chatId, messageIndex);

        if (messageList != null && !messageList.isEmpty()) {
            ApiResult<List<ChatMessage>> apiResult = new ApiResult<>(messageList);
            apiResult.setMessage("Messages for chat#" + chatId);
            deferredResult.setResult(apiResult);
        }
        return deferredResult;
    }

    /**
     * Get all waiting chats requested by users.
     * @return List of ChatRequests wrapped in {@link ApiResult} object as JSON data.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ApiResult<List<ChatRequest>> getWaitingChatRequests() {
        return new ApiResult<>(chatDao.getChatRequests(), ApiResult.STATUS_OK);
    }

    /**
     * creates a new chatRequest, sets the HttpResponse location header to include the newly created request ID
     * and returns the {@link ChatRequest} object to the user.
     * @param chatRequest {@link ChatRequest} object as JSON is required in the request body.
     *                    Only the nickname field has to be set, everything else will be generated later.
     * @return {@link ApiResult} with the created chatRequest as application/json.
     * HttpStatus code is 200 if the request succeeded.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ApiResult<ChatRequest> requestChat(@RequestBody ChatRequest chatRequest, HttpServletResponse response) {
        ChatRequest newChatRequest = chatDao.requestChat(chatRequest.getNickname());
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/chat/" + newChatRequest.getId()).build().toUri();
        response.addHeader("Location", uri.getRawPath());
        response.setStatus(HttpStatus.CREATED.value());
        return new ApiResult<>(newChatRequest, ApiResult.STATUS_OK);
    }

    /**
     * Closes the chat for the id specified in the url. Used with DELETE Http method.
     * Throws {@link HttpClientErrorException} no chat exists for the id.
     * @param chatId id of the chat is specified in the request url.
     * @return {@link ApiResult} object with no result body as JSON.
     */
    @RequestMapping(value = "/{chatId}", method = RequestMethod.DELETE)
    public @ResponseBody ApiResult closeChat(@PathVariable int chatId, HttpServletResponse response) {
        if (!chatDao.closeChat(chatId)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                    "Chat with id " + chatId + " doesn't exist.");
        } else {
            response.setStatus(HttpStatus.OK.value());
            return new ApiResult(ApiResult.STATUS_OK, "Chat closed successfully.");
        }
    }

}
