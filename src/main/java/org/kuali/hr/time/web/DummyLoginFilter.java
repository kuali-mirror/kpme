package org.kuali.hr.time.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.util.UrlPathHelper;

public class DummyLoginFilter implements Filter{
	private static final String LOGGED_IN_USER_KEY = "_LOGGED_IN_USER";
	private String loginPath;
	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	public void init(FilterConfig config) throws ServletException {
		loginPath = config.getInitParameter("loginPath");
		if (loginPath == null) {
			loginPath = "/WEB-INF/jsp/dummy_login.jsp";
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsreq = (HttpServletRequest) request;
			 String username = (String) hsreq.getSession().getAttribute(LOGGED_IN_USER_KEY);
			if (username == null) {
				username = ((HttpServletRequest) request).getRemoteUser();
				if (username == null) {
					username = request.getParameter("__login_user");
				}
				if (username != null) {
					hsreq.getSession().setAttribute(LOGGED_IN_USER_KEY, username);
					final String user = username;
					request = new HttpServletRequestWrapper(hsreq) {
						public String getRemoteUser() {
							return user;
						}
					};
				} else {
					String actionUrl = urlPathHelper.getOriginatingContextPath(hsreq) + urlPathHelper.getLookupPathForRequest((HttpServletRequest) request) + "?" + urlPathHelper.getOriginatingQueryString((HttpServletRequest) request);
					request.setAttribute("action_url", actionUrl);
					// no session has been established and this is not a login form submission, so
					// forward to login page
					request.getRequestDispatcher(loginPath).forward(request, response);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}
	@Override
	public void destroy() {
	}
	public static String getRemoteUser(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(LOGGED_IN_USER_KEY);
	}
}
