package kloSpringServer.controller;

import kloSpringServer.data.ChatDao;
import kloSpringServer.data.ChatDaoInMemoryImpl;
import kloSpringServer.model.ChatMessage;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 6.11.2013
 * Time: 23:29
 */
public class ChatControllerTest extends ControllerTest {
    private int chatId;
    ChatDao chatDao;
    ChatController controller;

    private ChatDao mockChatController() {
        chatDao = new ChatDaoInMemoryImpl();
        chatId = chatDao.requestChat().getId();
        chatDao.acceptChat(chatId);
        controller = new ChatController();
        controller.chatDao = chatDao;
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        return chatDao;
    }

    @Test
    public void testAddMessage() throws Exception {
        mockChatController();

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
                .andExpect(status().isOk());
    }

    @Test
    public void testGetChatMessages() throws Exception {
        mockChatController();
        ChatMessage message = new ChatMessage();
        message.setContent("kala");
        message.setWriter("kalamies");
        message.setIndex(0);
        chatDao.addMessage(chatId, message);

        MvcResult result = mockMvc.perform(get("/chat/" + chatId)
        )
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.result").isArray())
//                .andExpect(jsonPath("$.result[0].content").value(message.getContent()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetWaitingChatRequests() throws Exception {
        mockChatController();
        int id = chatDao.requestChat().getId();
        int id2 = chatDao.requestChat().getId();

        MvcResult result = mockMvc.perform(get("/chat/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result[1].id").value(CoreMatchers.anyOf(CoreMatchers.equalTo(id), CoreMatchers.equalTo(id2))))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testRequestChat() throws Exception {
        MvcResult result = mockMvc.perform(post("/chat/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").exists())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
