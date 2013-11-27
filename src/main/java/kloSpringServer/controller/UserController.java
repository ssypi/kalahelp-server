package kloSpringServer.controller;

import kloSpringServer.data.UserDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.User;
import kloSpringServer.service.UserAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 4.11.2013
 * Time: 0:32
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserAuthentication authentication;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ApiResult addUser(@RequestBody User user) {
        if (!user.validate()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid User");
        }
        authentication.createUser(user);
        return new ApiResult(null, 0);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ApiResult getUsers() {
        List<User> users = userDao.getUsers();
        return new ApiResult(users);
    }

    @RequestMapping(value = "/{username}/delete", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult deleteUser(@PathVariable String username) {
        logger.info("Deleting user: " + username);
        userDao.deleteUser(username);
        return new ApiResult(null, ApiResult.STATUS_OK);
    }




}
