package kloSpringServer.data;

import kloSpringServer.controller.ControllerTest;
import kloSpringServer.model.ReplyTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class JdbcTemplateDaoTest extends ControllerTest {

    @Autowired
    public TemplateDao templateDao;

    @Test
    public void testGetTemplateList() throws Exception {
        List<ReplyTemplate> templateList = templateDao.getTemplates();
        assertFalse(templateList.isEmpty());
        assertEquals("Kalamies", templateList.get(0).getName());
        assertEquals("alku", templateList.get(0).getBeginningPart());
        assertEquals("loppu", templateList.get(0).getEndPart());
        assertEquals("Kalamies", templateList.get(0).getCreator());

    }

    @Test
    public void testAddTemplate() throws Exception {
        ReplyTemplate template = new ReplyTemplate();
        template.setBeginningPart("Kalamies");
        template.setEndPart("Kuka ties");
        template.setName("Kala");
        template.setCreator("Kaalamies");

        templateDao.addTemplate(template);

    }
}
