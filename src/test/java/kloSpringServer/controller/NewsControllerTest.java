package kloSpringServer.controller;

import kloSpringServer.model.ApiResult;
import kloSpringServer.model.NewsItem;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class NewsControllerTest extends ControllerTest {
    @Test
    public void deletingNonExistingNewsShouldResultIn404() throws Exception {
        int newsId = 1235366;
        mockMvc.perform(delete("/news/" + newsId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void gettingNonExistingNewsShouldResultIn404() throws Exception {
        mockMvc.perform(get("/news/123456"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.result").doesNotExist())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testDeleteNews() throws Exception {
        int newsId = 2;

        MvcResult result = mockMvc.perform(delete("/news/"  + newsId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(200)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getNewsShouldReturnValidJson() throws Exception {
        MvcResult result = mockMvc.perform(get("/news/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is(0)))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.content").exists())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        String responseBody = result.getResponse().getContentAsString();

        ApiResult apiResult = (ApiResult) convertToObject(responseBody, ApiResult.class, NewsItem.class);
        NewsItem newsItem = (NewsItem) apiResult.getResult();
        assertEquals(1, newsItem.getId());
        assertEquals("Kalamiehen testi_uutinen", newsItem.getContent());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getLatestNewsShouldReturnMultipleNewsItems() throws Exception {
        MvcResult result = mockMvc.perform(get("/news/latest/5")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is(0)))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result[0].content").exists())
                .andExpect(jsonPath("$.result[1].content").exists())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ApiResult apiResult = convertToApiResult(responseBody, List.class, NewsItem.class);
        List<NewsItem> newsList = (List) apiResult.getResult();
        Assert.notEmpty(newsList);
        boolean contains = false;
        for (NewsItem newsItem : newsList) {
            if ("Kalamiehen testi_uutinen".equals(newsItem.getContent())) {
                contains = true;
            }
        }
        assertTrue(contains);
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
