package org.kuali.hr.time.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.config.ConfigContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TkLoginFilter implements Filter {

    private Filter dummyLoginFilter = new DummyLoginFilter();
    //TODO add your Filtering mechanism here
    private Filter userLoginFilter = new org.kuali.rice.kew.web.DummyLoginFilter();
    private static boolean testMode = false;
    public static String TEST_ID = "admin";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsRequest = (HttpServletRequest) request;
        if (getTestMode()) {
            hsRequest = new HttpServletRequestWrapper(hsRequest) {
                public String getRemoteUser() {
                    return TEST_ID;
                }
            };
            chain.doFilter(hsRequest, response);
        } else {
            applyRedirectHeader(request, response);
            getTargetFilter().doFilter(request, response, chain);
        }
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

    protected void applyRedirectHeader(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        int sessionExpiredTime = TKUtils.getSessionTimeoutTime();

        if (!StringUtils.contains(httpRequest.getRequestURI(), "/SessionInvalidateAction")) {
            if (sessionExpiredTime > 0) {
                Integer pageRedirectTime = sessionExpiredTime;

                httpResponse.setHeader("Refresh", pageRedirectTime + ";URL=" + httpRequest.getContextPath() + "/SessionInvalidateAction.do?methodToCall=invalidateUserSession");
            }
        }
    }


}
