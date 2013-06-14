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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.workarea.WorkArea;
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
	
	protected void setSearchFields(CalendarApprovalForm calendarApprovalForm) {
		super.setSearchFields(calendarApprovalForm);

		if (calendarApprovalForm.getCalendarDocument() != null) {
			calendarApprovalForm.setSelectedPayCalendarGroup(calendarApprovalForm.getCalendarDocument().getCalendarEntry().getCalendarName());

			for (Assignment assignment : calendarApprovalForm.getCalendarDocument().getAssignments()) {
				WorkArea workArea = HrServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), assignment.getEffectiveLocalDate());
				if (calendarApprovalForm.getDepartments().contains(workArea.getDept())) {
					calendarApprovalForm.setSelectedDept(workArea.getDept());
					break;
				}
			}
		}
	}
	
    protected void setCalendarFields(CalendarApprovalForm calendarApprovalForm) {
		Set<String> calendarYears = new TreeSet<String>(Collections.reverseOrder());
		List<CalendarEntry> calendarEntries = getCalendarEntries(calendarApprovalForm.getCalendarEntry());
		
	    for (CalendarEntry calendarEntry : calendarEntries) {
	    	String calendarEntryYear = calendarEntry.getBeginPeriodFullDateTime().toString("yyyy");
	    	calendarYears.add(calendarEntryYear);
	    }
	    
        String currentCalendarYear = calendarApprovalForm.getCalendarEntry().getBeginPeriodFullDateTime().toString("yyyy");
        String selectedCalendarYear = StringUtils.isNotBlank(calendarApprovalForm.getSelectedCalendarYear()) ? calendarApprovalForm.getSelectedCalendarYear() : currentCalendarYear;
	    
        calendarApprovalForm.setCalendarYears(new ArrayList<String>(calendarYears));
        calendarApprovalForm.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(ActionFormUtils.getAllCalendarEntriesForYear(calendarEntries, selectedCalendarYear), null));
	    
        calendarApprovalForm.setSelectedCalendarYear(selectedCalendarYear);
        calendarApprovalForm.setSelectedPayPeriod(calendarApprovalForm.getCalendarEntry().getHrCalendarEntryId());
	}

    protected List<CalendarEntry> getCalendarEntries(CalendarEntry currentCalendarEntry) {
		return HrServiceLocator.getCalendarEntryService().getAllCalendarEntriesForCalendarId(currentCalendarEntry.getHrCalendarId());
	}
	
	protected List<String> getSubListPrincipalIds(HttpServletRequest request, List<String> assignmentPrincipalIds) {
	    String page = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
	    // The paging index begins from 1, but the sublist index begins from 0.
	    // So the logic below sets the sublist begin index to 0 if the page number is null or equals 1
	    Integer beginIndex = StringUtils.isBlank(page) || StringUtils.equals(page, "1") ? 0 : (Integer.parseInt(page) - 1)*HrConstants.PAGE_SIZE;
	    Integer endIndex = beginIndex + HrConstants.PAGE_SIZE > assignmentPrincipalIds.size() ? assignmentPrincipalIds.size() : beginIndex + HrConstants.PAGE_SIZE;
	
	    return assignmentPrincipalIds.subList(beginIndex, endIndex);
	}

	protected Boolean getAscending(HttpServletRequest request) {
	    // returned value 1 = ascending; 2 = descending
	    String ascending = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_ORDER)));
	    return StringUtils.isEmpty(ascending) || StringUtils.equals(ascending, "1");
	}

	protected String getSortField(HttpServletRequest request) {
	    return request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_SORT)));
	}

}