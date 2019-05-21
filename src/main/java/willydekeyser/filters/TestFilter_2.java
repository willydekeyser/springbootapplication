package willydekeyser.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Servlet Filter implementation class TestFilter_2
 */
@Component
@WebFilter("/leden/*")
@Order(1)
public class TestFilter_2 implements Filter {

    /**
     * Default constructor. 
     */
    public TestFilter_2() {
    	System.out.println("Filter 2 - TestFilter_2()");
    }

	/**
	 * @see Filter#destroy()
	 */
    @Override
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
    	//System.out.println("Filter 2 - do filter ");
		
		HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpServletRequest httpReq = (HttpServletRequest) request;
        long currTime = System.currentTimeMillis();
        long expiryTime = currTime + httpReq.getSession().getMaxInactiveInterval() * 1000;
        Cookie cookie = new Cookie("serverTime", "" + currTime);
        cookie.setPath("/");
        httpResp.addCookie(cookie);
        if (httpReq.getRemoteUser() != null) {
            cookie = new Cookie("sessionExpiry", "" + expiryTime);
        } else {
            cookie = new Cookie("sessionExpiry", "" + currTime);
        }
        cookie.setPath("/");
        httpResp.addCookie(cookie);
        
        //System.out.println("TIME OUT: " + expiryTime + " - " + currTime);
        
		chain.doFilter(request, response);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("Filter 2 - init");
	}

}
