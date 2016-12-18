package com.wpw.dream.freemaker;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

public class TemplateViewResolver extends AbstractTemplateViewResolver {

	public TemplateViewResolver()
    {
        setViewClass(requiredViewClass());
    }

    protected Class requiredViewClass()
    {
      //  return org/springframework/web/servlet/view/freemarker/FreeMarkerView;
//        return RichFreeMarkerView.class;
        return RichFreeMarkerView.class;
    }
}
