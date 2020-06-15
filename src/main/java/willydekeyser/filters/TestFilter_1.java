package willydekeyser.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//@WebFilter(urlPatterns = "/leden/*")
//@Order(2)
public class TestFilter_1 implements Filter {

    public TestFilter_1() {
    	//System.out.println("Filter 1 - TestFilter_1");
    }

	public void destroy() {
		//System.out.println("Filter 1 - destroy");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//HttpServletRequest httpRequest = (HttpServletRequest) request;
		//HttpServletResponse httpResponse = (HttpServletResponse) response;
			
		//System.out.println("Filter 1 - doFilter IN: " + httpRequest.getRequestURI());
		//System.out.println("Filter 1 - doFilter IN: " + httpResponse.getContentType());
		
		chain.doFilter(request, response);
		
		//httpRequest = (HttpServletRequest) request;
		//httpResponse = (HttpServletResponse) response;
		
		//System.out.println("Filter 1 - doFilter UIT: " + httpRequest.getRequestURI());
		//System.out.println("Filter 1 - doFilter UIT: " + httpResponse.getContentType());
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		//System.out.println("Filter 1 - init");
	}

}
