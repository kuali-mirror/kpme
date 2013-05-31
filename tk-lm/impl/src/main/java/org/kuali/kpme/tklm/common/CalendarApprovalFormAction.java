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
package org.kuali.kpme.tklm.common;

import java.util.ArrayList;
import java.util.Collections;
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
import org.joda.time.LocalDate;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

public abstract class CalendarApprovalFormAction extends ApprovalFormAction {
	
	@Override
	protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
		if (!HrContext.isReviewer() && !HrContext.isAnyApprover() && !HrContext.isSystemAdmin() && !TkContext.isLocationAdmin() && !HrContext.isGlobalViewOnly() 
				&& !TkContext.isDepartmentViewOnly() && !TkContext.isDepartmentAdmin()) {
			throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "CalendarApprovalFormAction", "");
		}
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward actionForward = super.execute(mapping, form, request, response);
		
        CalendarApprovalForm calendarApprovalForm = (CalendarApprovalForm) form;
        
        setCalendarFields(request, calendarApprovalForm);
        
        CalendarEntry calendarEntry = null;
        if (calendarApprovalForm.getHrCalendarEntryId() != null) {
        	calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(calendarApprovalForm.getHrCalendarEntryId());
        } else {
        	Calendar calendar = HrServiceLocator.getCalendarService().getCalendarByGroup(calendarApprovalForm.getSelectedPayCalendarGroup());
            if (calendar != null) {
                calendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), LocalDate.now().toDateTimeAtStartOfDay());
            }
        }
        calendarApprovalForm.setHrCalendarEntryId(calendarEntry.getHrCalendarEntryId());
        calendarApprovalForm.setCalendarEntry(calendarEntry);
        calendarApprovalForm.setBeginCalendarEntryDate(calendarEntry.getBeginPeriodDateTime());
        calendarApprovalForm.setEndCalendarEntryDate(DateUtils.addMilliseconds(calendarEntry.getEndPeriodDateTime(),-1));
		
		CalendarEntry prevCalendarEntry = HrServiceLocator.getCalendarEntryService().getPreviousCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
		calendarApprovalForm.setPrevHrCalendarEntryId(prevCalendarEntry != null ? prevCalendarEntry.getHrCalendarEntryId() : null);
		
		CalendarEntry nextCalendarEntry = HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
		calendarApprovalForm.setNextHrCalendarEntryId(nextCalendarEntry != null ? nextCalendarEntry.getHrCalendarEntryId() : null);
		
		if (StringUtils.isBlank(calendarApprovalForm.getSelectedPayPeriod())) {
			calendarApprovalForm.setSelectedPayPeriod(calendarApprovalForm.getHrCalendarEntryId());
		}

		return actionForward;
	}
	
    protected void setCalendarFields(HttpServletRequest request, CalendarApprovalForm calendarApprovalForm) {
		Set<String> calendarYears = new TreeSet<String>(Collections.reverseOrder());
		List<CalendarEntry> calendarEntries = new ArrayList<CalendarEntry>();
		
		if (!StringUtils.isEmpty(request.getParameter("selectedCY"))) {
			calendarApprovalForm.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
		} else {
			if (calendarApprovalForm.getCalendarEntry() != null) {
				calendarApprovalForm.setSelectedCalendarYear(calendarApprovalForm.getCalendarEntry().getBeginPeriodFullDateTime().toString("yyyy"));
			} else {
				CalendarEntry calendarEntry = null;
		        if (calendarApprovalForm.getHrCalendarEntryId() != null) {
		        	calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(calendarApprovalForm.getHrCalendarEntryId());
		        } else {
		        	Calendar calendar = HrServiceLocator.getCalendarService().getCalendarByGroup(calendarApprovalForm.getSelectedPayCalendarGroup());
		            if (calendar != null) {
		                calendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), LocalDate.now().toDateTimeAtStartOfDay());
		            }
		        }
		        calendarApprovalForm.setSelectedCalendarYear(calendarEntry.getBeginPeriodFullDateTime().toString("yyyy"));
			}
		}
		
	    for (CalendarEntry calendarEntry : getCalendarEntries()) {
	    	String calendarEntryYear = calendarEntry.getBeginPeriodFullDateTime().toString("yyyy");
	    	calendarYears.add(calendarEntryYear);
	    	if (StringUtils.equals(calendarEntryYear, calendarApprovalForm.getSelectedCalendarYear())) {
	    		calendarEntries.add(calendarEntry);
	    	}
	    }
	    calendarApprovalForm.setCalendarYears(new ArrayList<String>(calendarYears));
		
		if (!StringUtils.isEmpty(request.getParameter("selectedPP"))) {
			calendarApprovalForm.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
			calendarApprovalForm.setHrCalendarEntryId(request.getParameter("selectedPP").toString());
		} else {
			calendarApprovalForm.setSelectedPayPeriod(calendarApprovalForm.getHrCalendarEntryId());
		}
		
		calendarApprovalForm.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(calendarEntries, null));
	}
    
    protected abstract List<CalendarEntry> getCalendarEntries();
	
	protected List<String> getSubListPrincipalIds(HttpServletRequest request, List<String> assignmentPrincipalIds) {
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
	    return StringUtils.isEmpty(ascending) || StringUtils.equals(ascending, "1") ? true : false;
	}

	protected String getSortField(HttpServletRequest request) {
	    return request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_SORT)));
	}

    // Triggered by changes of calendar year drop down list, reloads the pay period list
    public ActionForward changeCalendarYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CalendarApprovalForm taf = (CalendarApprovalForm) form;
    	if(!StringUtils.isEmpty(request.getParameter("selectedCY"))) {
    		taf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
    		setCalendarFields(request, taf);
    	}
    	return mapping.findForward("basic");
    }

    // Triggered by changes of pay period drop down list, reloads the whole page based on the selected pay period
    public ActionForward changePayPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      CalendarApprovalForm taf = (CalendarApprovalForm) form;
  	  if(!StringUtils.isEmpty(request.getParameter("selectedPP"))) {
  		  taf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
  		  CalendarEntry pce = HrServiceLocator.getCalendarEntryService()
  		  	.getCalendarEntry(request.getParameter("selectedPP").toString());
  		  if(pce != null) {
  			  taf.setCalendarEntry(pce);
  		  }
  	  }
  	  return mapping.findForward("basic");
	}

}