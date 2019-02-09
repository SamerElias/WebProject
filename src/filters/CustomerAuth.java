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

/**
 * Servlet Filter denying non-logged in customer from accessing servlets.
 */
@WebFilter({
	"/GetAllMSGS",
	"/UnlikeBook",
	"/LikeBook",
	"/GetBook",
	"/GetBookLikes",
	"/GetBookReview",
	"/GetBooksByGenre",
	"/GetBooksGenre",
	"/AddReview",
	"/SetReadingSession",
	"/PurchaseBook",
	"/GetOwnedStatus",
	"/GetOwnedBooks",
	"/GetFeaturedBooksServlet",
	"/Logout",
	"/ReadMSG"
	
})
public class CustomerAuth implements Filter {

    /**
     * Default constructor. 
     */
    public CustomerAuth() {
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
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
