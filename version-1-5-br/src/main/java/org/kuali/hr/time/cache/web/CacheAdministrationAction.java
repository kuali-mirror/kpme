package org.kuali.hr.time.cache.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.action.KualiAction;

public class CacheAdministrationAction extends KualiAction {
	
	private final static Logger LOG = Logger.getLogger(CacheAdministrationAction.class);

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CacheAdministrationActionForm cacheAdministrationActionForm = (CacheAdministrationActionForm) form;
		this.setCacheMap(request, cacheAdministrationActionForm);
		WebUtils.logRequestContents(LOG, Level.INFO, request);
		return mapping.findForward("basic");
	}
	
	public ActionForward flushGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CacheAdministrationActionForm cacheAdministrationActionForm = (CacheAdministrationActionForm) form;
		TkServiceLocator.getCacheManagerService().getCacheAdministrator().flushGroup(cacheAdministrationActionForm.getGroupKey());
		this.setCacheMap(request, cacheAdministrationActionForm);
		WebUtils.logRequestContents(LOG, Level.INFO, request);
		return mapping.findForward("basic");
	}

	
	public ActionForward flushItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CacheAdministrationActionForm cacheAdministrationActionForm = (CacheAdministrationActionForm) form;
		TkServiceLocator.getCacheManagerService().getCacheAdministrator().flushEntry(cacheAdministrationActionForm.getItemKey());
		this.setCacheMap(request, cacheAdministrationActionForm);
		WebUtils.logRequestContents(LOG, Level.INFO, request);
		return mapping.findForward("basic");
	}
	
	@SuppressWarnings("unchecked")
	private void setCacheMap(HttpServletRequest request, CacheAdministrationActionForm cacheAdministrationActionForm) {
		request.setAttribute("groups", (Map<String, Set<String>>) TkServiceLocator.getCacheManagerService().getCacheMap());
		cacheAdministrationActionForm.setGroups((Map<String, Set<String>>) request.getAttribute("groups"));
	}


	public ActionForward showGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CacheAdministrationActionForm cacheAdministrationActionForm = (CacheAdministrationActionForm) form;
		this.setCacheMap(request, cacheAdministrationActionForm);
		return mapping.findForward("basic");
	}

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isAuthorized = true;
//		try {
//			if (KIMServiceLocator.getGroupService().isMemberOfGroup(TKContext.getUser().getPrincipalId(), TkConstants.SUPER_USER)) {
//				isAuthorized = true;
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
		
		if (!isAuthorized) {
			LOG.error("******************************************************************");
			LOG.error("Unauthorized access by " + request.getRemoteHost() + " from " + request.getRemoteUser());
			LOG.error("******************************************************************");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/unauthorized.jsp");
			dispatcher.forward(request, response);
			return null;
		}
	    return super.execute(mapping, form, request, response);
    }
}
	