package org.kuali.kpme.edo.base.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoConstants.ConfigSettings;
import org.kuali.rice.core.api.config.property.ConfigContext;
//import org.kuali.rice.kew.web.DummyLoginFilter;
import org.kuali.rice.krad.web.filter.DummyLoginFilter;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lfox
 * Date: 9/20/12
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoLoginFilter implements Filter{
    //Filter casFilter = new HreCASFilter();
    Filter dummyLoginFilter = new DummyLoginFilter();
    private static boolean testMode;
    public static String TEST_NETWORK_ID;
	private static final String DONE_AUTHENTICATING = "_DONE_AUTHENTICATING";


    public void destroy() {
        this.getTargetFilter().destroy();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (getTestMode()) {
            filterChain.doFilter(request, response);
        } else {
            this.applyRedirectHeader(request, response);
            this.getTargetFilter().doFilter(request, response, filterChain);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        setTestMode();
        this.getTargetFilter().init(config);
    }

    protected static void setTestMode() {
        testMode = false;
    }

    public static boolean getTestMode() {
        return testMode;
    }

    protected Filter getTargetFilter() {
        return this.dummyLoginFilter;
        /*if (getTestMode()) {
            return this.dummyLoginFilter;
        } else {
            return this.casFilter;
        }*/
    }

    public static String getRemoteUser(HttpServletRequest request) {
        return (StringUtils.isEmpty(TEST_NETWORK_ID) ? "rpiercy": TEST_NETWORK_ID);
        /*if (getTestMode()) {
            return (StringUtils.isEmpty(TEST_NETWORK_ID) ? "rpiercy": TEST_NETWORK_ID);
        } else {
            return HreCASFilter.getRemoteUser(request);
        }*/
   }

    protected void applyRedirectHeader(ServletRequest request, ServletResponse response) {
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		int sessionExpiredTime = 0;
			if (!StringUtils.contains(httpRequest.getRequestURI(), "/SessionInvalidateAction"))  {
				if (StringUtils.isBlank(ConfigContext.getCurrentContextConfig().getProperty(EdoConstants.ConfigSettings.SESSION_TIMEOUT))) {
					ConfigContext.getCurrentContextConfig().putProperty(ConfigSettings.SESSION_TIMEOUT, "300");
				}

				sessionExpiredTime = Integer.parseInt(ConfigContext.getCurrentContextConfig().getProperty(EdoConstants.ConfigSettings.SESSION_TIMEOUT));
				if (sessionExpiredTime > 0) {
					Integer pageRedirectTime = sessionExpiredTime;
	                httpResponse.setHeader("Refresh", pageRedirectTime + ";URL=" + ConfigContext.getCurrentContextConfig().getProperty("application.url") + "/SessionInvalidateAction.do?methodToCall=invalidateUserCache");
				}
			}
		}


    /*public static final class HreCASFilter extends CASFilter {

        @Override
        protected boolean shouldPromptForSafeword(CASAuthentication authentication, HttpServletRequest request) throws IOException, ServletException {
            return false;
        }
        
        protected RedirectResponse establishCASAuthentication(HttpServletRequest request, HttpServletResponse response, Map authList) throws IOException, ServletException, SAXException, ParserConfigurationException {
            CASAuthentication authentication = (CASAuthentication) authList.get(cassvc);
            RedirectResponse redirectResponse = new RedirectResponse();
            redirectResponse.redirect = false;
            redirectResponse.redirectService = cassvc;
            
            // If the username list has the cassvc, they've been authenticated
            // just pass request on
            if (authentication != null) {
                log("CASFilter doFilter(): Already Authenticated");
                // if we're watching for susequent safeword authentication,
                // redirect to CAS to aquire new ticket
                *//**
                 *
                 * This is the difference from the original filter and prevents the redirect
                 * on every request
                 *
                 *//*
                if (shouldPromptForSafeword(authentication, request)) {
                    log("CASFilter doFilter(): watching for safeword, redirect to aquire new ticket");
                    // clear the exsiting authentication
                    authList.remove(cassvc);
                    redirectResponse.redirect = true;
                }
            } else {
                String casticket = request.getParameter("casticket");
                log("CASFilter doFilter(): casticket = " + casticket);
                if (casticket == null) {
                    // user hasn't been to CAS yet redirect them
                    log("CASFilter doFilter(): no casticket redirecting browser to CAS Server");
                    redirectResponse.redirect = true;
                } else {
                    // user has been to CAS but casticket hasn't been verified,
                    // otherwise we'd have filterCASBean in session
                    log("CASFilter doFilter(): casticket exists verifying it");
                    authentication = new CASAuthentication();
                    boolean valid = validate(authentication, cassvc, casticket, request);
                    if (!valid) {
                        // failed validation, bad casticket, user is going back
                        // to CAS to login and get new casticket
                        log("CASFilter doFilter(): casticket invalid redirect to browser");
                        log("CASFilter doFilter(): query_string = "
                                    + request.getQueryString());
                        redirectResponse.redirect = true;
                    } else {
                        // first check if we require safeword
                        if (!authentication.isSafewordAuthenticated() && shouldPromptForSafeword(authentication, request)) {
                            redirectResponse.redirect = true;
                            redirectResponse.redirectService = SAFEWORD_AUTH;
                        } else {
                            // user's casticket verified as good, adding to
                            // authentication list and passing request on
                            authList.put(cassvc, authentication);
                            log("CASFilter doFilter(): username = "
                                        + authentication.getUsername());
                            log("CASFilter doFilter(): safewordAuthenticated = "
                                        + authentication.isSafewordAuthenticated());
                            log("CASFilter doFilter(): authentication successful");
                        }
                    }
                }
            }
            return redirectResponse;
        }
        protected void setDoneAuthenticating(boolean doneAuthenticating, HttpServletRequest request) {
            request.getSession().setAttribute(DONE_AUTHENTICATING, doneAuthenticating);
        }

        protected boolean getDoneAuthenticing(HttpServletRequest request) {
            return request.getSession().getAttribute(DONE_AUTHENTICATING)==null?false:true;
        }

    }
    */
}
