package kloSpringServer.controller;

import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;

import static junit.framework.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("SpellCheckingInspection")
public class CategoryControllerTest extends ControllerTest {
    @Test
    public void shouldNotAllowDuplicateCategories() throws Exception {
        String json = convertToJson("kalamiehen gatogory", String.class);
        try {
            mockMvc.perform(post("/category/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
                    .andExpect(status().isOk());
            fail();
        } catch (Exception e) {
            //
        }

    }


}
