package org.kuali.hr.time.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.kew.web.UserLoginFilter;

public class TkLoginFilter implements Filter {
	
	private Filter dummyLoginFilter = new DummyLoginFilter();
	private Filter userLoginFilter = new UserLoginFilter();
	private static boolean testMode = false;
	public static String TEST_NETWORK_ID = "rkirkend";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(getTestMode()){
			chain.doFilter(request, response);
		}
		getTargetFilter().doFilter(request, response, chain);
	}
	@Override
	public void init(FilterConfig config) throws ServletException {
		setTestMode();
		this.getTargetFilter().init(config);
    }
	
	@Override
	public void destroy() {
		this.getTargetFilter().destroy();
	}
	
	protected Filter getTargetFilter() {
		if (getTestMode()) {
			return this.dummyLoginFilter;
		} else {
			return this.userLoginFilter;
		}
	}
	
	protected static void setTestMode() {
		testMode = new Boolean(ConfigContext.getCurrentContextConfig().getProperty("test.mode"));
	}

	public static boolean getTestMode() {
		return testMode;
	}

}
