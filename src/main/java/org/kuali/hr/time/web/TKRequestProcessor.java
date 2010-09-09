package org.kuali.hr.time.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.exceptions.UnauthorizedException;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKSessionState;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.kew.web.UserLoginFilter;
import org.kuali.rice.kew.web.session.UserSession;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.web.struts.action.KualiRequestProcessor;

public class TKRequestProcessor extends KualiRequestProcessor {
	private static final Logger LOG = Logger
			.getLogger(TKRequestProcessor.class);

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			TKContext.setHttpServletRequest(request);
			setUserOnContext(request);
			super.process(request, response);
		} catch (Exception e) {
			LOG.warn("Caught exception processing request", e);
		}
	}

	public void setUserOnContext(HttpServletRequest request) {
		if (request != null && StringUtils.isNotBlank(request.getParameter("backdoorId"))) {
			if (StringUtils.equalsIgnoreCase(TKUtils.getEnvironment(), "prd")) {
				throw new UnauthorizedException(
						"Cannot backdoor in production environment");
			}

			TKUser tkUser = new TKUser();
			UserSession userSession = UserLoginFilter.getUserSession(request);
			Person backdoorPerson = userSession.getBackdoorPerson();
			Person person = userSession.getActualPerson();

			tkUser.setBackdoorPerson(backdoorPerson);
			tkUser.setActualPerson(person);
			TKContext.setBackdoorUser(tkUser);
			TKContext.setUser(tkUser);

			TKSessionState tkSessionState = new TKSessionState(TKContext.getHttpServletRequest().getSession());
			tkSessionState.setTargetEmployee(tkUser);
			tkSessionState.setBackdoorUser(tkUser);
		} else {
			if (new Boolean(ConfigContext.getCurrentContextConfig().getProperty("test.mode"))) {
				request.setAttribute("principalName", TkLoginFilter.TEST_ID);
			} else {
				UserSession userSession = UserLoginFilter.getUserSession(request);
				Person person = userSession.getActualPerson();
				TKUser tkUser = new TKUser();
				tkUser.setActualPerson(person);
				List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(person.getPrincipalId(), new java.sql.Date(System.currentTimeMillis()));
				tkUser.setJobs(jobs);
				TKContext.setUser(tkUser);
			}

		}
	}
}
