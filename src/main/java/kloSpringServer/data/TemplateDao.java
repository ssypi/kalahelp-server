package kloSpringServer.data;

import kloSpringServer.model.ReplyTemplate;

import java.util.List;

public interface TemplateDao {
    void addTemplate(ReplyTemplate template);

    void deleteTemplate(String templateName);

    List<ReplyTemplate> getTemplates();
}
