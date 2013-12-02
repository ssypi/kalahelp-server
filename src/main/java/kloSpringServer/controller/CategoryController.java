package kloSpringServer.controller;

import kloSpringServer.data.CategoryDao;
import kloSpringServer.model.ApiResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new ApiResult();
    }

    @RequestMapping(value = "/{category}/delete", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ApiResult deleteCategory2(@PathVariable String category) {
        logger.info(category);
        categoryDao.deleteCategory(category);
        return new ApiResult();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    ApiResult<List<String>> getCategories() {
        return new ApiResult<>(categoryDao.getCategories());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ApiResult addCategory(@RequestBody String category) {
        categoryDao.saveCategory(category);
        return new ApiResult();
    }
}

