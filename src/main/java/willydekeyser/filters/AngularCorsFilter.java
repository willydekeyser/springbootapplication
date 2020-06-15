package willydekeyser.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class AngularCorsFilter implements Filter {

	public AngularCorsFilter() {
	    System.out.println("AngularCorsFilter init");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    
	    System.out.println("AngularCorsFilter voor: " + request.getLocalAddr() + " - " + request.getMethod());
	    
	    Enumeration<String> headerNames = request.getHeaderNames();

	    if (headerNames != null) {
	        while (headerNames.hasMoreElements()) {
	            String name = headerNames.nextElement();
	            System.out.println("Header: " + name + " value:" + request.getHeader(name));
	        }
	    }
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    
        chain.doFilter(req, res);
        
        System.out.println("AngularCorsFilter na: " + response.getHeaderNames());
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authorization");
        
        response.getHeaderNames().forEach(headerName ->
        response.getHeaders(headerName).forEach(headerValue ->
            System.out.println("HEADER: " +  headerName + " value: " + headerValue)));
	}

	  @Override
	  public void init(FilterConfig filterConfig) {}

	  @Override
	  public void destroy() {}
}
