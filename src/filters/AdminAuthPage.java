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
 * Servlet Filter for denying a normal customer or non-logged in user from accessing admin pages.
 */
@WebFilter({
			"/ADMINhomepage.html",
			"/ADMINmail.html",
			"/ADMINprofile.html",
			"/ADMINtransactions.html",
			"/ADMINusersList.html",
			"/ADMINebookPage.html",
			"/ADMINhelpMe.html",
			"/ADMINrespond.html"
	})
public class AdminAuthPage implements Filter {

    /**
     * Default constructor. 
     */
    public AdminAuthPage() {
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
		if(!session.getAttribute("username").equals(AppConstants.ADMIN)) {
			httpRes.sendRedirect(httpReq.getContextPath() + "/index.html");
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
