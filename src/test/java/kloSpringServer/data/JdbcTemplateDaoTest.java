package kloSpringServer.data;

import kloSpringServer.model.ReplyTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: Evige
 * Date: 17.12.2013
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"/testContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcTemplateDaoTest {

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
