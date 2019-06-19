package willydekeyser.aspect;

import java.security.Principal;
import java.util.Enumeration;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import willydekeyser.admin.CustomException;

@Component
@Aspect
public class LoggingAspect {
		
	@Pointcut("execution(* willydekeyser.*.*.*(..))")
	private void generalPointcut() {}
	
	@Pointcut("execution(* willydekeyser.service.*.*(..))")
	private void servicePointcut() {}
	
	@Pointcut("execution(* willydekeyser.service.*.add*(..))")
	private void addServicePointcut() {}
	
	@Pointcut("execution(* willydekeyser.service.*.update*(..))")
	private void updateServicePointcut() {}
	
	@Pointcut("execution(* willydekeyser.service.*.delete*(..))")
	private void deleteServicePointcut() {}
	
	@Before("addServicePointcut()")
	public void addlogAll(JoinPoint joitPoint) {
		System.out.println("log All Before: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Principal principal = request.getUserPrincipal();
		System.err.println("User naam: " + principal.getName());
	}
	
	@Before("updateServicePointcut()")
	public void updatelogAll(JoinPoint joitPoint) {
		System.out.println("log All Before: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Principal pricipal = request.getUserPrincipal();
		System.err.println("User naam: " + pricipal.getName());
	}
	
	@Before("deleteServicePointcut()")
	public void deletelogAll(JoinPoint joitPoint) {
		System.out.println("log All Before: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Principal pricipal = request.getUserPrincipal();
		System.err.println("User naam: " + pricipal.getName());
	}
	
	@AfterReturning(pointcut="servicePointcut()", returning = "retVal")
	public void logAllAfter(JoinPoint joitPoint, Object retVal) {
		System.out.println("log All AfterReturning: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName() + " - " + retVal.toString());
	}
	
	//@Before("execution(* willydekeyser.dao.impl.*.*(..)) ")
	public void LoggingAdvice(JoinPoint joitPoint) {
		System.out.println("Advice run DAO: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(* willydekeyser.service.impl.*.*(..)) ")
	public void AdviceServiceImpl(JoinPoint joitPoint) {
		System.out.println("Advice run service: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(* willydekeyser.controller.*.*(..)) ")
	public void AdviceController(JoinPoint joitPoint) {
		System.out.println("Advice run controller: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(public String getOmschrijving())")
	public void AdviceModel(JoinPoint joitPoint) {
		System.out.println("Aspect run MODEL: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	@Before("execution(* willydekeyser.controller.LedenController.*(..))")
	public void AdviceAll(JoinPoint joitPoint) throws AuthException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		System.err.println("Aspect run ALL: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
		System.err.println("REQUEST: " + request.getUserPrincipal().getName() + " - " + request.getLocalName());
		Enumeration<String> headerNames = request.getHeaderNames();
		String test_header = request.getHeader("header");
		System.out.println("HEADER voor: " + test_header);
		if (test_header == null) {
			System.out.println("HEADER: " + test_header);
			throw new CustomException("Header ERROR");
		}
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
		String xAuth = request.getHeader("header");
		if (xAuth == null) {
			throw new CustomException("Exception message from AOP on unauthorized access NULL");
		} {
			if (!xAuth.equals("Willy De Keyser")) {
				throw new CustomException("Exception message from AOP on unauthorized access");
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
        //return null;

    }
	
	//@Around("execution(* willydekeyser.controller.LedenController.*(..))")
    public Object methodPointcut(ProceedingJoinPoint jointPoint) throws Throwable{
		//Object result = new Object();
		System.out.println("Voor proceed: " + jointPoint.toString());
		try {
			//result = (ModelAndView) jointPoint.proceed();
		} finally {
			
		}
		System.out.println("Na proceed: " + jointPoint.toString());
		return "redirect:/testen.html";   
    }
}
