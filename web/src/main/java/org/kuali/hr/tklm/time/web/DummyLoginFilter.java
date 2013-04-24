/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.tklm.time.web;

import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

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
