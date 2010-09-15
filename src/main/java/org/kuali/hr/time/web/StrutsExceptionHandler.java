package org.kuali.hr.time.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class StrutsExceptionHandler extends ExceptionHandler {
	
	private static final Logger LOG = Logger.getLogger(StrutsExceptionHandler.class);

	@Override
	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance, HttpServletRequest request, HttpServletResponse response) throws ServletException {
		LOG.error(ex);
		return super.execute(ex, ae, mapping, formInstance, request, response);
	}

}
