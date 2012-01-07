package org.kuali.hr.time.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.exceptions.UnauthorizedException;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.kew.web.UserLoginFilter;
import org.kuali.rice.kew.web.session.UserSession;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.service.KNSServiceLocator;
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
		
		TKUser user = TKContext.getUser();
		
		String header = " Browser: "+request.getHeader("User-Agent");
		if(StringUtils.isBlank(header)){
			header = "No header found";
		}
		
		response.setHeader(PRIVACY_POLICY_KEY, PRIVACY_POLICY_VALUE);
		
		long totalTime = System.currentTimeMillis() - startTime.getTime();
		
		LOG.info(new StringBuffer("Finished processing :: PERFORMANCE :: [[[Total Time: " + totalTime + "ms]]] ").append(request.getRequestURL()).append(" for ").append(user.getPrincipalId()).append(header));
		
	}

	@Override
	/**
	 * This method calls into our backdoor and TKUser setup.
	 */
	protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
		boolean status = super.processPreprocess(request, response);

		setUserOnContext(request);

		return status;
	}

	/**
	 * This method exists because the UnitTests need to set the request as well.
	 *
	 * @param request
	 */
	public void setUserOnContext(HttpServletRequest request) {
		if (request != null) {
			UserSession userSession = UserLoginFilter.getUserSession(request);

			Person person = null;
			Person backdoorPerson = null;
            Person targetPerson = null;

			if(userSession!=null){
				backdoorPerson = userSession.getBackdoorPerson();
				person = userSession.getActualPerson();
                targetPerson = (Person)userSession.getObjectMap().get(TkConstants.TK_TARGET_USER_PERSON);
			}

			// Check for test mode; if not test mode check for backDoor validity.
			if (new Boolean(ConfigContext.getCurrentContextConfig().getProperty("test.mode"))) {
				request.setAttribute("principalName", TkLoginFilter.TEST_ID);
				person = KIMServiceLocator.getPersonService().getPerson(TkLoginFilter.TEST_ID);
				backdoorPerson = null;
			} else {
				if (backdoorPerson != null) {
					LOG.debug("Backdoor user in use:" + backdoorPerson.getPrincipalId());
					if (KNSServiceLocator.getKualiConfigurationService().isProductionEnvironment()) {
						userSession.clearBackdoor();
						// TODO : we could simply clear the backdoor as well.
						throw new UnauthorizedException("Cannot backdoor in production environment");
					}
				}
			}

            // Current date is sufficient for loading of current roles and permissions.
			TKUser tkUser = TkServiceLocator.getUserService().buildTkUser(person, backdoorPerson, targetPerson, TKUtils.getCurrentDate());
			TKContext.setUser(tkUser);
		} else {
			// Bail with Exception
			throw new RuntimeException("Null HttpServletRequest while setting user.");
		}
	}

}
