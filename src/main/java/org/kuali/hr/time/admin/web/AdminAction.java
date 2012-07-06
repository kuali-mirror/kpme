package org.kuali.hr.time.admin.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.user.service.UserServiceImpl;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

public class AdminAction extends TkAction {

	private static final Logger LOG = Logger.getLogger(AdminAction.class);

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TKUser user = TKContext.getUser();
        AdminActionForm adminForm = (AdminActionForm) form;

        if (StringUtils.equals(methodToCall, "targetEmployee") || StringUtils.equals(methodToCall, "changeEmployee") || StringUtils.equals(methodToCall, "clearBackdoor") || StringUtils.equals(methodToCall, "clearChangeUser")) {
            // Handle security validation in targetEmployee action, we may need
            // to check the document for validity, since the user may not
            // necessarily be a system administrator.
        } else {
        	Person changePerson = null;
        	if(StringUtils.isNotBlank(adminForm.getChangeTargetPrincipalName())){
        		changePerson = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(adminForm.getChangeTargetPrincipalName());
        	}
            if (user == null ||
            		(!user.isSystemAdmin()
            			&& !user.isLocationAdmin()
            			&& !user.isDepartmentAdmin()
            			&& !user.isGlobalViewOnly()
            			&& !user.isDepartmentViewOnly()
            			&& (changePerson == null ||
            			 !user.getCurrentRoles().isApproverForPerson(changePerson.getPrincipalId())
            			&& (changePerson == null || !user.getCurrentRoles().isDocumentReadable(adminForm.getDocumentId()))))
            		)  {
                throw new AuthorizationException("", "AdminAction", "");
            }
        }
    }

    public ActionForward backdoor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AdminActionForm adminForm = (AdminActionForm) form;
        TKUser tkUser = TKContext.getUser();

        if (tkUser.getCurrentRoles().isSystemAdmin()) {
            if (StringUtils.isNotBlank(adminForm.getBackdoorPrincipalName())) {

                Person backdoorPerson = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(adminForm.getBackdoorPrincipalName());

                if (backdoorPerson != null && tkUser != null) {
                    UserSession userSession = GlobalVariables.getUserSession();

                    GlobalVariables.getUserSession().setBackdoorUser(backdoorPerson.getPrincipalId());

                    tkUser.setBackdoorPerson(backdoorPerson);

                    UserServiceImpl.loadRoles(tkUser);
                    TKContext.setUser(tkUser);
                    LOG.debug("\n\n" + TKContext.getUser().getActualPerson().getPrincipalName() + " backdoors as : " + backdoorPerson.getPrincipalName() + "\n\n");
                }
            }
        } else {
            LOG.warn("Non-Admin user attempting to backdoor.");
            return mapping.findForward("unauthorized");
        }

		return mapping.findForward("basic");
	}

    public ActionForward changeEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AdminActionForm adminForm = (AdminActionForm) form;
        TKUser tkUser = TKContext.getUser();

        if (StringUtils.isNotBlank(adminForm.getChangeTargetPrincipalName())) {
        	Person changePerson = KimApiServiceLocator.getPersonService().getPerson(adminForm.getChangeTargetPrincipalName());
        	
	        if (changePerson != null && tkUser != null) {
	            if (tkUser.getCurrentRoles().isSystemAdmin()
	                	|| tkUser.getCurrentRoles().isGlobalViewOnly()
	                	|| tkUser.getCurrentRoles().isDepartmentAdminForPerson(changePerson.getPrincipalId())
	                	|| tkUser.getCurrentRoles().isDeptViewOnlyForPerson(changePerson.getPrincipalId())
	                	|| tkUser.getCurrentRoles().isLocationAdminForPerson(changePerson.getPrincipalId())
	                	|| tkUser.getCurrentRoles().isTimesheetReviewerForPerson(changePerson.getPrincipalId())
	                	|| tkUser.getCurrentRoles().isApproverForPerson(changePerson.getPrincipalId())) {
		                	
		            UserSession userSession = GlobalVariables.getUserSession();
		            userSession.getObjectMap().put(TkConstants.TK_TARGET_USER_PERSON, changePerson);
	
		            if (StringUtils.isNotEmpty(adminForm.getReturnUrl())) {
		                userSession.getObjectMap().put(TkConstants.TK_TARGET_USER_RETURN, adminForm.getReturnUrl());
		            }
		
		            tkUser.setTargetPerson(changePerson);
		            UserServiceImpl.loadRoles(tkUser);
		            TKContext.setUser(tkUser);
		
		            LOG.debug("\n\n" + TKContext.getUser().getActualPerson().getPrincipalName() + " change employee as : " + changePerson.getPrincipalName() + "\n\n");
	            }else {
	                LOG.warn("Non-Admin user attempting to backdoor.");
	                return mapping.findForward("unauthorized");
	            }
	        }
        }
        String returnAction = "/PersonInfo.do";
        if (StringUtils.isNotEmpty(adminForm.getTargetUrl())) {
            returnAction = adminForm.getTargetUrl();
        }

        return new ActionRedirect(returnAction);
    }
    
    //http://156.56.177.225:8080/tk-dev/Admin.do?methodToCall=changeEmployee&documentId=19018&changeTargetPrincipalId=1659102154&targetUrl=TimeDetail.do%3FdocumentId=19018&returnUrl=TimeApproval.do
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.equals(request.getParameter("command"), "displayDocSearchView")
        		|| StringUtils.equals(request.getParameter("command"), "displayActionListView") ) {
        	final String docId = (String)request.getParameter("docId");
        	TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
        	final String targetPrincipalId = td.getPrincipalId();
        	
        	return new ActionRedirect("/Admin.do?methodToCall=changeEmployee&documentId"+docId+"&changeTargetPrincipalId="+targetPrincipalId+"&targetUrl=TimeDetail.do%3FdocmentId="+docId+"&returnUrl=TimeApproval.do");
        }
    	
    	return mapping.findForward("basic");
    }
    

    public ActionForward deleteTimesheet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AdminActionForm adminForm = (AdminActionForm) form;
    	String documentId = adminForm.getDeleteDocumentId();
    	if(StringUtils.isNotBlank(documentId)){
    		System.out.println("Deleting timesheet: "+documentId);
    		TkServiceLocator.getTimesheetService().deleteTimesheet(documentId);
}
    	return mapping.findForward("basic");
    }
    
    public ActionForward runAccruals(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	AdminActionForm adminForm = (AdminActionForm) form;
    	
    	DateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
    	if (StringUtils.isNotBlank(adminForm.getAccrualPrincipalId())) {
    		String accrualPrincipalId = adminForm.getAccrualPrincipalId();
    		if (StringUtils.isNotBlank(adminForm.getFormStartDate()) && StringUtils.isNotBlank(adminForm.getFormEndDate())) {
    			java.util.Date parsedStartDate = formater.parse(adminForm.getFormStartDate());
    			java.sql.Date startDate= new java.sql.Date(parsedStartDate.getTime());

    			java.util.Date parsedEndDate = formater.parse(adminForm.getFormEndDate());
    			java.sql.Date endDate= new java.sql.Date(parsedEndDate.getTime());
    	
    			System.out.println("AccrualServiceImpl.runAccrual() called with Principal: "+accrualPrincipalId+" Start: "+startDate.toString()+" End: "+endDate.toString());
    			TkServiceLocator.getLeaveAccrualService().runAccrual(accrualPrincipalId, startDate, endDate);
    	}else {
    		System.out.println("AccrualServiceImpl.runAccrual() called with Principal: "+accrualPrincipalId);
    		TkServiceLocator.getLeaveAccrualService().runAccrual(accrualPrincipalId);
    		}
    	}
    	return mapping.findForward("basic");
    }
    public ActionForward clearAccruals(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	AdminActionForm adminForm = (AdminActionForm) form;
    	
    	if(StringUtils.isNotBlank(adminForm.getAccrualPrincipalId())) {
    		System.out.println("AccrualServiceImpl.clearAccrual() called with Principal: "+adminForm.getAccrualPrincipalId());
    		adminForm.setAccrualPrincipalId("");
    	} 
    	if(StringUtils.isNotBlank(adminForm.getFormStartDate())) {
    		System.out.println("AccrualServiceImpl.clearAccrual() called with Start Date: "+adminForm.getFormStartDate());
    		adminForm.setFormStartDate("");
    	} 
    	if(StringUtils.isNotBlank(adminForm.getFormEndDate())) {
    		System.out.println("AccrualServiceImpl.clearAccrual() called with End Date: "+adminForm.getFormEndDate());
    		adminForm.setFormEndDate("");
    	} 
    	
    	return mapping.findForward("basic");
    }
}
