package kloSpringServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import kloSpringServer.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
* Created with IntelliJ IDEA.
* User: kala
* Date: 20.10.2013
* Time: 1:04
*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(locations = {"/testContext.xml", "/applicationContext.xml"})
public class SessionControllerTest {

    @Autowired
    WebApplicationContext wac;
//    @Autowired MockHttpSession session;
//    @Autowired MockHttpServletRequest request;


    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }



    @Test
    public void testNewSessionForUser() throws Exception {
        User user = new User();
        user.setUsername("Kalamies");
        user.setPassword("kalasana");
        user.setEmail("asd");

        String userAsJson = "";

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            userAsJson = ow.writeValueAsString(user);

            ObjectMapper mapper = new ObjectMapper();
            User userFromJson = mapper.readValue(userAsJson, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Unable to map User/json");
        }
//        System.out.println(userAsJson);

        mockMvc.perform(post("/session/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(userAsJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(0)))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.token", notNullValue()));
//                .andReturn();
//        System.out.println(result.getResponse().getContentAsString());
    }
}
