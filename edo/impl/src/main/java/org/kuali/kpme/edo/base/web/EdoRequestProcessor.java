package org.kuali.kpme.edo.base.web;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.kns.web.struts.action.KualiRequestProcessor;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: lfox
 * Date: 8/27/12
 * Time: 8:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoRequestProcessor extends KualiRequestProcessor {
	static final Logger LOG = Logger.getLogger(EdoRequestProcessor.class);
	@Override
	protected boolean processPreprocess(HttpServletRequest request,
			HttpServletResponse response) {
		
		GlobalVariables.getMessageMap().clearErrorMessages();
		GlobalVariables.getMessageMap().clearErrorPath();
		
		EdoContext.setHttpServletRequest(request);
	//	HreContext.setHttpServletRequest(request);
		setUserOnSession(request);
		return super.processPreprocess(request, response);
	}
	@Override
	protected void processPopulate(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping)
			throws ServletException {
		ConvertUtils.deregister(java.sql.Date.class);
		//ConvertUtils.register(new FarDateConverter(), java.sql.Date.class);
		ConvertUtils.deregister(java.math.BigDecimal.class);
		//ConvertUtils.register(new FarBigDecimalConverter(), java.math.BigDecimal.class);

		super.processPopulate(request, response, form, mapping);
		if(form instanceof EdoForm){
			EdoContext.setEdoForm((EdoForm)form);
		}
	}
	
	private void setUserOnSession(HttpServletRequest request){
		if(EdoContext.getUser()==null){
			EdoUser edoUser = null;
			edoUser = EdoServiceLocator.getAuthorizationService().getEdoUser(EdoLoginFilter.getRemoteUser(request));
			EdoContext.setUser(edoUser);
		}
	}

}
