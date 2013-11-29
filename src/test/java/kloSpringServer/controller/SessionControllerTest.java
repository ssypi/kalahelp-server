package kloSpringServer.controller;

import kloSpringServer.model.ApiResult;
import kloSpringServer.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@DirtiesContext
@ContextConfiguration(locations = {"/testContext.xml"})
public class SessionControllerTest extends ControllerTest {

    @Test
    public void testWrongPassword() throws Exception {
        User user = new User();
        user.setUsername("kalamies");
        user.setPassword("incorrectpassword");

        String userAsJson = convertToJson(user, User.class);

        mockMvc.perform(post("/session/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(userAsJson)
        )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status", is(ApiResult.STATUS_ERROR)))
                .andExpect(jsonPath("$.result").doesNotExist());
    }

    @Test
    public void shouldReturnBadRequestForInvalidUser() throws Exception {
        User user = new User();
        user.setUsername("k");
        user.setPassword("a");
        assertFalse(user.validate());

        mockMvc.perform(post("/session/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(user, User.class)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnauthorizedForWrongUserPass() throws Exception {
        User user = new User();
        user.setUsername("kalamiehenkaveri");
        user.setPassword("kalasalasana");

        String userAsJson = convertToJson(user, User.class);

        mockMvc.perform(post("/session/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(userAsJson)
        )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status", is(ApiResult.STATUS_ERROR)))
                .andExpect(jsonPath("$.result").doesNotExist());
    }

    @Test
    public void testCorrectUserAndPassword() throws Exception {
        User user = new User();
        user.setUsername("kalamies");
        user.setPassword("kalasalasana");

        String userAsJson = convertToJson(user, User.class);

        mockMvc.perform(post("/session/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(userAsJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ApiResult.STATUS_OK)))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.token", notNullValue()));
    }
}
