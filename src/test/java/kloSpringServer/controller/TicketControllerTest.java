package kloSpringServer.controller;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: Joona
 * Date: 24.11.2013
 * Time: 22:13
 */
public class TicketControllerTest extends ControllerTest {
    @Test
    public void testGetLatestTickets() throws Exception {
        mockMvc.perform(get("/ticket/latest/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(0)))
                .andExpect(jsonPath("$.result").exists());


    }
}
