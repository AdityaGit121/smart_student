package com.smartstudent.sis.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for centralized cross-cutting logging across student services and controllers.
 * 
 * Demonstrates:
 * - @Aspect: Declares this class as an AspectJ aspect.
 * - @Component: Registers the aspect as a Spring-managed singleton bean.
 * - Pointcuts: Reusable expressions defining interception targets.
 * - @Before: Advices executing prior to method target invocation.
 * - @AfterReturning: Advices executing upon successful return.
 * - @AfterThrowing: Advices executing when an exception is propagated.
 * - @Around: Surrounds method execution to measure execution duration.
 */
@Aspect
@Component
public class StudentLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(StudentLoggingAspect.class);

    // =========================================================================
    // POINTCUT DEFINITIONS
    // =========================================================================

    /**
     * Pointcut that matches all methods within the StudentService interface or implementation.
     */
    @Pointcut("execution(* com.smartstudent.sis.service.StudentService.*(..))")
    public void studentServiceMethods() {}

    /**
     * Pointcut that matches all methods within StudentController.
     */
    @Pointcut("execution(* com.smartstudent.sis.controller.StudentController.*(..))")
    public void studentControllerMethods() {}

    // =========================================================================
    // BEFORE ADVICE - ACTIVITY-SPECIFIC LOGGING
    // =========================================================================

    /**
     * Intercepts student creation specifically to log activity.
     */
    @Before("execution(* com.smartstudent.sis.service.StudentService.createStudent(..))")
    public void logCreatingStudent(JoinPoint joinPoint) {
        logger.info(">>> [AOP ACTIVITY] Creating student... Arguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Intercepts student queries (getStudentById, getAllStudents, getStudentsByDepartment).
     */
    @Before("execution(* com.smartstudent.sis.service.StudentService.get*(..))")
    public void logFetchingStudent(JoinPoint joinPoint) {
        logger.info(">>> [AOP ACTIVITY] Fetching student... Method: {}, Arguments: {}",
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Intercepts student updates.
     */
    @Before("execution(* com.smartstudent.sis.service.StudentService.updateStudent(..))")
    public void logUpdatingStudent(JoinPoint joinPoint) {
        logger.info(">>> [AOP ACTIVITY] Updating student... Method: {}, Arguments: {}",
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Intercepts student deletions.
     */
    @Before("execution(* com.smartstudent.sis.service.StudentService.deleteStudent(..))")
    public void logDeletingStudent(JoinPoint joinPoint) {
        logger.info(">>> [AOP ACTIVITY] Deleting student... Method: {}, Arguments: {}",
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    // =========================================================================
    // AROUND ADVICE - METHOD EXECUTION TIME PROFILING
    // =========================================================================

    /**
     * Intercepts all service methods to measure execution duration.
     * Demonstrates ProceedingJoinPoint.proceed()
     */
    @Around("studentServiceMethods()")
    public Object profileMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        logger.info(">>> [AOP START] Executing method: {}", methodName);

        try {
            Object result = proceedingJoinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("<<< [AOP FINISH] Completed method: {} in {} ms", methodName, executionTime);
            return result;
        } catch (Throwable ex) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.warn("<<< [AOP FAILED] Method: {} failed after {} ms with message: {}",
                    methodName, executionTime, ex.getMessage());
            throw ex;
        }
    }

    // =========================================================================
    // AFTER RETURNING ADVICE - SUCCESS AUDIT
    // =========================================================================

    @AfterReturning(pointcut = "studentServiceMethods()", returning = "result")
    public void logMethodReturnSuccess(JoinPoint joinPoint, Object result) {
        logger.debug("[AOP SUCCESS] Method '{}' returned successfully.", joinPoint.getSignature().getName());
    }

    // =========================================================================
    // AFTER THROWING ADVICE - EXCEPTION AUDIT
    // =========================================================================

    @AfterThrowing(pointcut = "studentServiceMethods() || studentControllerMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        logger.error("[AOP EXCEPTION] Exception in method: '{}' with arguments: {} | Error: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()),
                exception.getMessage());
    }
}
