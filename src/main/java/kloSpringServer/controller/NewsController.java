package kloSpringServer.controller;

import kloSpringServer.data.NewsDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.NewsItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 10.10.2013
 * Time: 0:34
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/news")
public class NewsController {

    private static final Logger logger = Logger.getLogger(NewsController.class);

    @Autowired
    private NewsDao newsDao;

    @RequestMapping(value = "/{newsId}", method = RequestMethod.GET)
    public @ResponseBody
    ApiResult getNewsById(@PathVariable int newsId) {
        return new ApiResult(newsDao.getNewsById(newsId));
    }

    @RequestMapping(value = "/latest/{count}", method = RequestMethod.GET)
    public @ResponseBody
    ApiResult getLatestNews(@PathVariable int count) {
        return new ApiResult(newsDao.getLatestNews(count));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
//    @Transactional
    public ResponseEntity<?> addNewsItem(@RequestBody NewsItem newsItem) {
        NewsItem created = newsDao.addNews(newsItem);

        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;

        if (created != null) {
            int id = created.getId();
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/news/" + id).build().toUri();
            headers.setLocation(uri);
            status = HttpStatus.CREATED;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Void>(headers, status);
    }
}

