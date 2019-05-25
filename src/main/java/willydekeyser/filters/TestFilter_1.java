package willydekeyser.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.core.annotation.Order;

/**
 * Servlet Filter implementation class TestFilter
 */
@WebFilter(urlPatterns = "/leden/*")
@Order(2)
public class TestFilter_1 implements Filter {

    /**
     * Default constructor. 
     */
    public TestFilter_1() {
    	//System.out.println("Filter 1 - TestFilter_1");
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		//System.out.println("Filter 1 - destroy");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
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

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		//System.out.println("Filter 1 - init");
	}

}
