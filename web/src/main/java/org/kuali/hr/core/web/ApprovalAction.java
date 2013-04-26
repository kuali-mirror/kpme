/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.core.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.HrConstants;
import org.kuali.hr.core.bo.calendar.Calendar;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.bo.workarea.WorkArea;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.time.person.TKPerson;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

public class ApprovalAction extends HrAction{

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (form instanceof HrForm) {
           String methodToCall = ((HrForm)form).getMethodToCall();
           if(StringUtils.isEmpty(methodToCall)) {
        	   super.execute(mapping, form, request, response);
        	   return loadApprovalTab(mapping, form, request, response); 
           }
        }
        return super.execute(mapping, form, request, response);
    }
	
	public ActionForward loadApprovalTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("basic");
	}
	
	protected List<TKPerson> getSubListPrincipalIds(HttpServletRequest request, List<TKPerson> assignmentPrincipalIds) {
	    String page = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
	    // The paging index begins from 1, but the sublist index begins from 0.
	    // So the logic below sets the sublist begin index to 0 if the page number is null or equals 1
	    Integer beginIndex = StringUtils.isBlank(page) || StringUtils.equals(page, "1") ? 0 : (Integer.parseInt(page) - 1)*HrConstants.PAGE_SIZE;
	    Integer endIndex = beginIndex + HrConstants.PAGE_SIZE > assignmentPrincipalIds.size() ? assignmentPrincipalIds.size() : beginIndex + HrConstants.PAGE_SIZE;
	
	    return assignmentPrincipalIds.subList(beginIndex, endIndex);
	}

	protected Boolean isAscending(HttpServletRequest request) {
	    // returned value 1 = ascending; 2 = descending
	    String ascending = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_ORDER)));
	    return StringUtils.equals(ascending, "1") ? true : false;
	}

	protected String getSortField(HttpServletRequest request) {
	    return request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_SORT)));
	}
	
	protected void checkTKAuthorization(ActionForm form, String methodToCall)
			throws AuthorizationException {
			    if (!TKContext.isReviewer() && !TKContext.isAnyApprover() && !TKContext.isSystemAdmin()
			    		&& !TKContext.isLocationAdmin() && !TKContext.isGlobalViewOnly() && !TKContext.isDepartmentViewOnly()
			    		&& !TKContext.isDepartmentAdmin()) {
			        throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "ApprovalAction", "");
			    }
			}
	
	protected void resetMainFields(ActionForm form) {
		ApprovalForm taf = (ApprovalForm) form;
		taf.setSearchField(null);
		taf.setSearchTerm(null);
		taf.setSelectedWorkArea(null);
		taf.setSelectedDept(null);
		taf.setPayBeginDate(null);
		taf.setPayEndDate(null);
		taf.setHrPyCalendarEntryId(null);
	}
	
	protected void setupDocumentOnFormContext(HttpServletRequest request,
			ApprovalForm taf, CalendarEntry payCalendarEntry, String page) {
		if(payCalendarEntry == null) {
			return;
		}
		taf.setHrPyCalendarId(payCalendarEntry.getHrCalendarId());
		taf.setHrPyCalendarEntryId(payCalendarEntry.getHrCalendarEntryId());
		taf.setPayBeginDate(payCalendarEntry.getBeginPeriodDateTime());
		taf.setPayEndDate(DateUtils.addMilliseconds(payCalendarEntry.getEndPeriodDateTime(),-1));
		
		CalendarEntry prevPayCalendarEntry = HrServiceLocator.getCalendarEntryService().getPreviousCalendarEntryByCalendarId(taf.getHrPyCalendarId(), payCalendarEntry);
		if (prevPayCalendarEntry != null) {
		    taf.setPrevPayCalendarId(prevPayCalendarEntry.getHrCalendarEntryId());
		} else {
		    taf.setPrevPayCalendarId(null);
		}
		
		CalendarEntry nextPayCalendarEntry = HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(taf.getHrPyCalendarId(), payCalendarEntry);
		if (nextPayCalendarEntry != null) {
		    taf.setNextPayCalendarId(nextPayCalendarEntry.getHrCalendarEntryId());
		} else {
		    taf.setNextPayCalendarId(null);
		}	
		if (StringUtils.isBlank(page)) {
	        String principalId = GlobalVariables.getUserSession().getPrincipalId();
			Set<String> departments = new TreeSet<String>();
			departments.addAll(TkServiceLocator.getHRRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.REVIEWER.getRoleName(), new DateTime(), true));
			departments.addAll(TkServiceLocator.getHRRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));
			departments.addAll(TkServiceLocator.getHRRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true));
		    taf.setDepartments(new ArrayList<String>(departments));
		    
		    if (taf.getDepartments().size() == 1 || taf.getSelectedDept() != null) {
		    	if (StringUtils.isEmpty(taf.getSelectedDept()))
		    		taf.setSelectedDept(taf.getDepartments().get(0));
		        
		    	List<WorkArea> workAreaObjs = HrServiceLocator.getWorkAreaService().getWorkAreas(taf.getSelectedDept(), LocalDate.fromDateFields(taf.getPayBeginDate()));
		        for (WorkArea workAreaObj : workAreaObjs) {
		        	Long workArea = workAreaObj.getWorkArea();
		        	String description = workAreaObj.getDescription();
		        	
		        	if (TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.REVIEWER.getRoleName(), workArea, new DateTime())
		        			|| TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, new DateTime())
		        			|| TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), workArea, new DateTime())) {
		        		taf.getWorkAreaDescr().put(workArea, description + "(" + workArea + ")");
		        	}
		        }
		    }
		}

	}
	
    public ActionForward gotoCurrentPayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String page = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));         
    	ApprovalForm taf = (ApprovalForm) form;
    	DateTime currentDate = new LocalDate().toDateTimeAtStartOfDay();
        Calendar currentPayCalendar = HrServiceLocator.getCalendarService().getCalendarByGroup(taf.getSelectedPayCalendarGroup());
        CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(currentPayCalendar.getHrCalendarId(), currentDate);
        taf.setPayCalendarEntry(payCalendarEntry);
        taf.setSelectedCalendarYear(new SimpleDateFormat("yyyy").format(payCalendarEntry.getBeginPeriodDate()));
        taf.setSelectedPayPeriod(payCalendarEntry.getHrCalendarEntryId());
        populateCalendarAndPayPeriodLists(request, taf);
        setupDocumentOnFormContext(request, taf, payCalendarEntry, page);
        return mapping.findForward("basic");
    }
    
   
    
    // Triggered by changes of calendar year drop down list, reloads the pay period list
    public ActionForward changeCalendarYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ApprovalForm taf = (ApprovalForm) form;
    	if(!StringUtils.isEmpty(request.getParameter("selectedCY"))) {
    		taf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
    		populateCalendarAndPayPeriodLists(request, taf);
    	}
    	return mapping.findForward("basic");
    }

    // Triggered by changes of pay period drop down list, reloads the whole page based on the selected pay period
    public ActionForward changePayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      String page = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
      ApprovalForm taf = (ApprovalForm) form;
  	  if(!StringUtils.isEmpty(request.getParameter("selectedPP"))) {
  		  taf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
  		  CalendarEntry pce = HrServiceLocator.getCalendarEntryService()
  		  	.getCalendarEntry(request.getParameter("selectedPP").toString());
  		  if(pce != null) {
  			  taf.setPayCalendarEntry(pce);
  			  setupDocumentOnFormContext(request, taf, pce, page);
  		  }
  	  }
  	  return mapping.findForward("basic");
	}
    // sets the CalendarYear and Pay Period lists. Should be overridden by subclasses
    protected void populateCalendarAndPayPeriodLists(HttpServletRequest request, ApprovalForm taf) {
    	
    }


}
