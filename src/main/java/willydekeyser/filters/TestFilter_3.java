package willydekeyser.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

//@Component
//@Order(100)
public class TestFilter_3 extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		System.out.println("Filter OncePerRequestFilter voor: ");
		filterChain.doFilter(request, response);
		System.out.println("Filter OncePerRequestFilter na: ");
	}

}
