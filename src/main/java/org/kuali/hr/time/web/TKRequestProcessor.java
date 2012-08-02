package org.kuali.hr.time.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.web.struts.action.KualiRequestProcessor;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;

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
		
        String principalId = GlobalVariables.getUserSession().getPrincipalId();
		
		String header = " Browser: "+request.getHeader("User-Agent");
		if(StringUtils.isBlank(header)){
			header = "No header found";
		}
		
		response.setHeader(PRIVACY_POLICY_KEY, PRIVACY_POLICY_VALUE);
		
		long totalTime = System.currentTimeMillis() - startTime.getTime();
		
		LOG.info(new StringBuffer("Finished processing :: PERFORMANCE :: [[[Total Time: " + totalTime + "ms]]] ").append(request.getRequestURL()).append(" for ").append(principalId).append(header));
		
	}

	@Override
	/**
	 * This method calls into our backdoor and TKUser setup.
	 */
	protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
		boolean status = super.processPreprocess(request, response);
		
		setUserOnContext();

		return status;
	}
	
	/**
	 * This method exists because the UnitTests need to set the request as well.
	 */
	public void setUserOnContext() {
		// Check for test mode; if not test mode check for backDoor validity.
		if (new Boolean(ConfigContext.getCurrentContextConfig().getProperty("test.mode"))) {
            GlobalVariables.setUserSession(new UserSession(TkLoginFilter.TEST_ID));
			//GlobalVariables.getUserSession().setBackdoorUser(TkLoginFilter.TEST_ID);
		}

        //Person person = GlobalVariables.getUserSession().getActualPerson();
        //Person targetPerson = (Person) GlobalVariables.getUserSession().getObjectMap().get(TkConstants.TK_TARGET_USER_PERSON);

        // Current date is sufficient for loading of current roles and permissions.
		//TKUser tkUser = TKUser.getUser(targetPerson, TKUtils.getCurrentDate());
        //TKUser.setTargetPerson(targetPerson);
		//TKContext.setUser(tkUser);
	}

}
