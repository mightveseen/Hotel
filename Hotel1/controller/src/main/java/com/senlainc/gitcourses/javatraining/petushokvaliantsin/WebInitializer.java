package com.senlainc.gitcourses.javatraining.petushokvaliantsin;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.AppConfig;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.SecurityConfig;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.WebConfig;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.exceptionhandler.CustomExceptionHandler;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class, SecurityConfig.class, CustomExceptionHandler.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
