package org.kuali.hr.time.mobile.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.kuali.hr.time.util.TKContext;

public class TkMobileFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsRequest = (HttpServletRequest) request;
        TKContext.clear();
        TKContext.setHttpServletRequest(hsRequest);
		chain.doFilter(hsRequest, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
