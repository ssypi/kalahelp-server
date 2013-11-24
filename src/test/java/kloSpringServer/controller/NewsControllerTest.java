package kloSpringServer.controller;

import kloSpringServer.model.NewsItem;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 19.10.2013
 * Time: 3:27
 */
public class NewsControllerTest extends ControllerTest {

    @Test
    public void testDeleteNews() throws Exception {
        int newsId = 4;

        MvcResult result = mockMvc.perform(delete("/news/" + newsId)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(0)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

//    @Test
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
        mockMvc.perform(get("/news/latest/5")
                .accept(MediaType.APPLICATION_JSON)
        )
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
        newsItem.setWriter("2");

        String json = convertToJson(newsItem, NewsItem.class);

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
