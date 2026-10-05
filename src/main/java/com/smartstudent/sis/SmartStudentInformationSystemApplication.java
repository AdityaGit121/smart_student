package com.smartstudent.sis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main Entry Point for the Smart Student Information System.
 * 
 * Demonstrates:
 * - @SpringBootApplication: Combines @Configuration, @EnableAutoConfiguration, and @ComponentScan.
 * - @EnableAspectJAutoProxy: Enables processing of Spring AOP components marked with @Aspect.
 * - @EnableTransactionManagement: Enables Spring's declarative transaction management via @Transactional.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class SmartStudentInformationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartStudentInformationSystemApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("  SMART STUDENT INFORMATION SYSTEM STARTED SUCCESSFULLY!");
        System.out.println("  REST Base URL: http://localhost:8080/api/students");
        System.out.println("  SOAP WSDL URL: http://localhost:8080/ws/studentResults.wsdl");
        System.out.println("==================================================================");
    }
}
