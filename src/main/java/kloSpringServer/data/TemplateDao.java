package kloSpringServer.data;

import kloSpringServer.model.ReplyTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Evige
 * Date: 17.12.2013
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public interface TemplateDao {
    void addTemplate(ReplyTemplate template);

    void deleteTemplate(String templateName);

    List<ReplyTemplate> getTemplates();
}
