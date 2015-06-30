package edu.searchahouse.admin.config.mvc;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @Bean
    public TemplateResolver defaultTemplateResolver() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/thymeleaf/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false); // for development mode.

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(defaultTemplateResolver());
        templateEngine.addDialect(new LayoutDialect()); // Dialect that understand syntax like: layout:decorator or layout:fragment, etc.
        // integration.
        return templateEngine;
    }

    /**
     * Handles all views. This view resolver will be executed as first one by Spring.
     */
    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver vr = new ThymeleafViewResolver();
        vr.setTemplateEngine(templateEngine());
        vr.setCharacterEncoding("UTF-8");
        vr.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return vr;
    }

}
