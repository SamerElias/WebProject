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
 * Servlet Filter to redirect already logged in customers to homepage/ landing page.
 */
@WebFilter({ 
	"/index.html",
	"/Register.html"
			
})
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
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
		if(session != null) {
			if(session.getAttribute("username").equals(AppConstants.ADMIN)) {
				httpRes.sendRedirect(httpReq.getContextPath() + "/ADMINhomepage.html");
			}else {
			httpRes.sendRedirect(httpReq.getContextPath() + "/homepage.html");
			}
			httpRes.setStatus(HttpServletResponse.SC_OK);	
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
