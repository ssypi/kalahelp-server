package kloSpringServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import kloSpringServer.model.NewsItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 19.10.2013
 * Time: 3:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration({"/testContext.xml"})
public class NewsControllerTest {

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
    public void testGetNewsById() throws Exception {
        mockMvc.perform(get("/news/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is(0)))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.content").exists());

//       String content = result.getResponse().getContentAsString();
    }

    @Test
    public void testGetLatestNews() throws Exception {
        mockMvc.perform(get("/news/latest/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is(0)))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result[0].content").exists());
    }

    @Test
    public void testAddNewsItem() throws Exception {
        NewsItem newsItem = new NewsItem();
        newsItem.setContent("Kalakala");
        newsItem.setWriter(2);

        String json = "";

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(newsItem);

            ObjectMapper mapper = new ObjectMapper();
            NewsItem newsFromJson = mapper.readValue(json, NewsItem.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Unable to map NewsItem/json");
        }

        mockMvc.perform(post("/news/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
        )
                .andExpect(status().isCreated()) // success
                .andExpect(header().string("Location", containsString("/news/")))
                .andExpect(header().string("Location", not(containsString("/news/0")))); // not id 0
    }
}
