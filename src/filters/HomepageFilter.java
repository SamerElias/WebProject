package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import resources.AppConstants;

/**
 * Servlet Filter redirecting Admin or customer to his homepage.
 */
@WebFilter("/homepage.html")
public class HomepageFilter implements Filter {

    /**
     * Default constructor. 
     */
    public HomepageFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		HttpSession session =  httpReq.getSession(false);
		if(session == null) {
			httpRes.sendRedirect(httpReq.getContextPath() + "/index.html");
			httpRes.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
			return;
		}
		if(session.getAttribute("username").equals(AppConstants.ADMIN)) {
			httpRes.sendRedirect(httpReq.getContextPath() + "/ADMINhomepage.html");
			httpRes.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
