package kloSpringServer.controller;

import kloSpringServer.data.ChatDao;
import kloSpringServer.data.ChatDaoInMemoryImpl;
import kloSpringServer.model.ChatMessage;
import kloSpringServer.model.ChatRequest;
import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ChatControllerTest extends ControllerTest {
    private static final Logger logger = Logger.getLogger(ChatControllerTest.class);
    private int chatId;
    private ChatDao chatDao;

    @Override
    @Before
    public void setUp() throws Exception {
        chatDao = new ChatDaoInMemoryImpl();
        chatId = chatDao.requestChat("Kalamies").getId();
//        chatDao.acceptChat(chatId);
        ChatController controller = new ChatController();
        controller.chatDao = chatDao;
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testAddMessage() throws Exception {
        ChatMessage message = new ChatMessage();
        message.setContent("kalakalakala");
        message.setWriter("kalakalamies");

        String json = convertToJson(message, ChatMessage.class);

        mockMvc.perform(post("/chat/" + chatId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }
//
// TODO: FIX : test doesn't work because of long polling? empty response from get
//    @Test
//    public void testGetChatMessages() throws Exception {
//        ChatMessage message = new ChatMessage();
//        message.setContent("kala");
//        message.setWriter("kalamies");
//        message.setIndex(0);
//        chatDao.addMessage(chatId, message);
//
//        logger.debug("debug: " + chatId);
//
//        String json = convertToJson(message, ChatMessage.class);
//
//        MvcResult result = mockMvc.perform(get("/chat/" + chatId)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
////                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
////                .andExpect(jsonPath("$.result").isArray())
////                .andExpect(jsonPath("$.result[0].content").value(message.getContent()))
//                .andReturn();
//
//
//        logger.debug("response: " + result.getResponse().getContentAsString());
//    }

    @Test
    public void testGetWaitingChatRequests() throws Exception {
        int id = chatDao.requestChat("Kalamies").getId();
        int id2 = chatId;

        List<ChatRequest> list = chatDao.getChatRequests();
        assertEquals(2, list.size());

        MvcResult result = mockMvc.perform(get("/chat/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result[1].id").value(CoreMatchers.anyOf(CoreMatchers.equalTo(id), CoreMatchers.equalTo(id2))))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testRequestChat() throws Exception {
        ChatRequest request = new ChatRequest();
        request.setNickname("Kala");
        String json = convertToJson(request, ChatRequest.class);

        MvcResult result = mockMvc.perform(post("/chat/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
        )
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
