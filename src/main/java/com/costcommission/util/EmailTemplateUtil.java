package com.costcommission.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Iterator;
import java.util.Map;

@Component
public class EmailTemplateUtil {

    @Autowired
    private TemplateEngine templateEngine;

    public String getEmailTemplateData(String templateName, Map<String, Object> itemdata) {

        Context context = new Context();

        if (itemdata != null) {
            Iterator itr = itemdata.entrySet().iterator();
            while(itr.hasNext()) {
                Map.Entry<String, Object> data = (Map.Entry<String, Object>) itr.next();
                context.setVariable(data.getKey(), data.getValue().toString());
            }
        }
        return templateEngine.process(templateName, context);
    }

}
