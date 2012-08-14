package org.kuali.hr.time.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.web.struts.action.KualiRequestProcessor;

public class TKRequestProcessor extends KualiRequestProcessor {
	
	private static final Logger LOG = Logger.getLogger(TKRequestProcessor.class);
	private static final String PRIVACY_POLICY_KEY = "P3P";
	private static final String PRIVACY_POLICY_VALUE = "CP=\"CAO DSP COR CURa ADMa DEVa CUSo TAIa PSAa PSDa OUR STP ONL UNI COM NAV INT DEM STA PRE\"";
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Make sure ThreadLocal context is clean before doing anything else -
        // Tomcat uses a threadpool, and the ThreadLocals do not automatically die.
		
		Date startTime = new Date();
		
		TKContext.clear();
		TKContext.setHttpServletRequest(request);
		super.process(request, response);

		String header = " Browser: " + request.getHeader("User-Agent");
		if (StringUtils.isBlank(header)) {
			header = "No header found";
		}
		
		response.setHeader(PRIVACY_POLICY_KEY, PRIVACY_POLICY_VALUE);
		
		long totalTime = System.currentTimeMillis() - startTime.getTime();
		
		LOG.info(new StringBuffer("Finished processing :: PERFORMANCE :: [[[Total Time: " + totalTime + "ms]]] ").append(request.getRequestURL()).append(header));
		
	}

}