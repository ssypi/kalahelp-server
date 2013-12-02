package kloSpringServer.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.NewsItem;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    ApiResult convertToApiResult(String json, Class<?>... clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object object;
        if (clazz.length == 1) {
            object = mapper.readValue(json, TypeFactory.defaultInstance().constructParametricType(ApiResult.class, clazz[0]));
        } else {
            JavaType type = TypeFactory.defaultInstance().constructParametricType(clazz[0], clazz[1]);
            object = mapper.readValue(json, TypeFactory.defaultInstance().constructParametricType(ApiResult.class, type));
        }
//        return mapper.readValue(json, TypeFactory.defaultInstance().uncheckedSimpleType(clazz));
        return (ApiResult) object;
    }

    /**
     * Magic, don't touch
     */
     Object convertToObject(String json, Class<?>... clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if (clazz.length == 1) {
            return mapper.readValue(json, clazz[0]);
        }
        Class<?> claz = clazz[0];

        Class<?>[] classes = new Class[clazz.length-1];

        System.out.println(clazz.length);
        System.arraycopy(clazz, 1, classes, 0, clazz.length - 1);

//        return mapper.readValue(json, TypeFactory.defaultInstance().uncheckedSimpleType(clazz));
        Object object = mapper.readValue(json, TypeFactory.defaultInstance().constructParametricType(claz, classes));
         if (object.getClass() != claz) {
             return null;
         }
         return object;
    }

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
        return jsonString;
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}

