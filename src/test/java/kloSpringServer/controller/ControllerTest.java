package kloSpringServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.util.AssertionErrors.fail;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 4.11.2013
 * Time: 0:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@DirtiesContext
@ContextConfiguration(locations = {"/testContext.xml"})
public abstract class ControllerTest {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    String convertToJson(Object object, Class clazz) throws IOException {
        String jsonString = null;
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            jsonString = ow.writeValueAsString(object);
            ObjectMapper mapper = new ObjectMapper();
            mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Unable to map to/from json");
        }
        return  jsonString;
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}

