package kloSpringServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import kloSpringServer.model.NewsItem;
import kloSpringServer.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 4.11.2013
 * Time: 0:36
 */
public class UserControllerTest extends ControllerTest {
    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("Kalamies");
        user.setPassword("salsana");

        String json = convertToJson(user, User.class);

        mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
        )
                .andExpect(status().isOk()); // success
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/user/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result[0].username").exists());



    }
}
