package willydekeyser.aspect;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class LoggingAspect {
	
	@Pointcut("execution(* willydekeyser.model.*.*(..))")
	private void generalPointcut() {
		
	}
	
	@Before("generalPointcut()")
	public void logAll(JoinPoint joitPoint) {
		//System.out.println("log All: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}

	//@Before("execution(* willydekeyser.dao.impl.*.*(..)) ")
	public void LoggingAdvice(JoinPoint joitPoint) {
		//System.out.println("Advice run DAO: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(* willydekeyser.service.impl.*.*(..)) ")
	public void AdviceServiceImpl(JoinPoint joitPoint) {
		//System.out.println("Advice run service: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(* willydekeyser.controller.*.*(..)) ")
	public void AdviceController(JoinPoint joitPoint) {
		//System.out.println("Advice run controller: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(public String getOmschrijving())")
	public void AdviceModel(JoinPoint joitPoint) {
		//System.out.println("Aspect run MODEL: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(* willydekeyser.controller.LedenController.*(..))")
	public void AdviceAll(JoinPoint joitPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		//System.err.println("Aspect run ALL: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
		System.err.println("REQUEST: " + request.getUserPrincipal().getName() + " - " + request.getLocalName());
		Enumeration<String> headerNames = request.getHeaderNames();
		//String test_header = request.getHeader("header");
		//System.out.println("HEADER voor: " + test_header);
		//if (test_header.isEmpty() || test_header == null) {
		//	System.out.println("HEADER: " + test_header);
		//	throw new IllegalAccessError("Header ERROR");
		//}
		System.err.println("HEADERS: ---------------------------------------- ");
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			System.err.print("Header: " + headerName);
			System.err.print(" ");
			Enumeration<String> headers = request.getHeaders(headerName);
			while (headers.hasMoreElements()) {
				String headerValue = headers.nextElement();
				System.err.print(" " + headerValue);
				System.err.println(" ");
			}
		}
			 
	}
	
	//@Around("execution(* willydekeyser.controller.*.*(..)) && target(service)")
	public Object logServiceAccess(ProceedingJoinPoint joinPoint, Object service) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Controller execution time: " + service.getClass().getSimpleName() + " - " + totalTime + "ms - " + result.toString());
        return result;

    }
	
}
