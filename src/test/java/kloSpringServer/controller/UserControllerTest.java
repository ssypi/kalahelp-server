package kloSpringServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import kloSpringServer.model.NewsItem;
import kloSpringServer.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertFalse;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    public void addUserShouldNotAcceptInvalidUser() throws Exception {
        User user = new User();
        user.setUsername("asd asd asd");
        user.setPassword("kal");
        assertFalse(user.validate());

        String userJson = convertToJson(user, User.class);

        try {
            mockMvc.perform(post("/user/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(userJson)
            )
                    .andExpect(status().isBadRequest()); // todo: fix: not called
            Assert.fail("Did not throw exception like expected.");
        } catch (Exception e) {
            // ignore
        }
    }

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
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/user/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result[0].username").exists());
    }

    @Test
    public void shouldNotAllowDeleteLastUser() throws Exception {
        mockMvc.perform(delete("/user/kalamies"))
                .andExpect(status().isOk());
        try {
            mockMvc.perform(delete("/user/kalamies2"))
                    .andExpect(status().isBadRequest());
            Assert.fail(); // we expect an exception to be thrown
        } catch (Exception e) {
            // ignore
        }
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/kalamies"))
                .andExpect(status().isOk());
    }
}
