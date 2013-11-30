package kloSpringServer.controller;

import kloSpringServer.data.CategoryDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.NewsItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joona
 * Date: 1.12.2013
 * Time: 0:54
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/category")
public class CategoryController {

    private static final Logger logger = Logger.getLogger(NewsController.class);

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "/{category}", method = RequestMethod.DELETE, produces = "application/json")
    public
    @ResponseBody
    ApiResult deleteCategory(@PathVariable String category) {
        logger.info(category);
        categoryDao.deleteCategory(category);
        return new ApiResult(null, ApiResult.STATUS_OK);
    }

    @RequestMapping(value = "/{category}/delete", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ApiResult deleteCategory2(@PathVariable String category) {
        logger.info(category);
        categoryDao.deleteCategory(category);
        return new ApiResult(null, ApiResult.STATUS_OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    ApiResult getCategories() {
        return new ApiResult(categoryDao.getCategories());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ApiResult addCategory(@RequestBody String category) {
        categoryDao.saveCategory(category);
        return new ApiResult(null, ApiResult.STATUS_OK);
    }
}

