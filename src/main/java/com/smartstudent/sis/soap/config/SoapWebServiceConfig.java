package com.smartstudent.sis.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Spring Web Services (SOAP) Configuration.
 * 
 * Demonstrates:
 * - @EnableWs: Enables Spring WS message handling and endpoint routing.
 * - MessageDispatcherServlet: The Spring-WS equivalent of Spring MVC's DispatcherServlet.
 * - DefaultWsdl11Definition: Dynamically exposes a standard WSDL 1.1 document from the XSD schema.
 *   Exposed at URL: http://localhost:8080/ws/studentResults.wsdl
 */
@EnableWs
@Configuration
public class SoapWebServiceConfig {

    /**
     * Registers the Spring-WS MessageDispatcherServlet to route SOAP requests under /ws/*
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /**
     * Exposes dynamic WSDL 1.1 from the XSD schema.
     * The bean name 'studentResults' determines the WSDL URL: /ws/studentResults.wsdl
     */
    @Bean(name = "studentResults")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema studentResultSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("StudentResultsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.smartstudent.com/sis/soap");
        wsdl11Definition.setSchema(studentResultSchema);
        return wsdl11Definition;
    }

    /**
     * Loads the student-result.xsd XML schema definition file from the classpath.
     */
    @Bean
    public XsdSchema studentResultSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/student-result.xsd"));
    }
}
