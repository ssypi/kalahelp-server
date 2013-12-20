package kloSpringServer.controller;

import kloSpringServer.data.TemplateDao;
import kloSpringServer.model.ApiResult;
import kloSpringServer.model.ReplyTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/template")
/**
 * Created with IntelliJ IDEA.
 * User: Evige
 * Date: 17.12.2013
 * Time: 19:02
 * To change this template use File | Settings | File Templates.
 */
public class TemplateController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(TemplateController.class);

    @Autowired
    private TemplateDao templateDao;

    @RequestMapping(value = "/{templateName}", method = RequestMethod.DELETE, produces = "application/json")
    public
    @ResponseBody
    ApiResult deleteTemplate(@PathVariable String templateName) {
        logger.info(templateName);
        templateDao.deleteTemplate(templateName);
        return new ApiResult();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    ApiResult<List<ReplyTemplate>> getTemplates() {
        return new ApiResult<>(templateDao.getTemplates());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ApiResult addTemplate(@RequestBody ReplyTemplate template) {
        templateDao.addTemplate(template);
        return new ApiResult();
    }
}
