/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.batch;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.lm.accrual.service.AccrualService;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.leaveplan.service.LeavePlanService;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.service.PrincipalHRAttributesService;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AccrualJob implements Job {

	private static AccrualService ACCRUAL_SERVICE;
	private static AssignmentService ASSIGNMENT_SERVICE;
	private static LeavePlanService LEAVE_PLAN_SERVICE;
	private static PrincipalHRAttributesService PRINCIPAL_HR_ATTRIBUTES_SERVICE;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date asOfDate = TKUtils.getCurrentDate();
		List<Assignment> assignments = getAssignmentService().getActiveAssignments(asOfDate);
		
		for (Assignment assignment : assignments) {
			if (assignment.getJob().isEligibleForLeave()) {
				String principalId = assignment.getPrincipalId();
				
				PrincipalHRAttributes principalHRAttributes = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
				if (principalHRAttributes != null) {
					LeavePlan leavePlan = getLeavePlanService().getLeavePlan(principalHRAttributes.getLeavePlan(), principalHRAttributes.getEffectiveDate());
					if (leavePlan != null) {
						DateTime endDate = new DateTime(asOfDate).plusMonths(Integer.parseInt(leavePlan.getPlanningMonths()));
						getAccrualService().runAccrual(principalId, new java.sql.Date(asOfDate.getTime()), new java.sql.Date(endDate.toDate().getTime()), true, TkConstants.BATCH_JOB_USER_PRINCIPAL_ID);
					}
				}
			}
		}
	}

	public static AccrualService getAccrualService() {
		return ACCRUAL_SERVICE;
	}

	public static void setAccrualService(AccrualService accrualService) {
		ACCRUAL_SERVICE = accrualService;
	}

	public static AssignmentService getAssignmentService() {
		return ASSIGNMENT_SERVICE;
	}

	public static void setAssignmentService(AssignmentService assignmentService) {
		ASSIGNMENT_SERVICE = assignmentService;
	}

	public static LeavePlanService getLeavePlanService() {
		return LEAVE_PLAN_SERVICE;
	}

	public static void setLeavePlanService(LeavePlanService leavePlanService) {
		LEAVE_PLAN_SERVICE = leavePlanService;
	}

	public static PrincipalHRAttributesService getPrincipalHRAttributesService() {
		return PRINCIPAL_HR_ATTRIBUTES_SERVICE;
	}

	public static void setPrincipalHRAttributesService(PrincipalHRAttributesService principalHRAttributesService) {
		PRINCIPAL_HR_ATTRIBUTES_SERVICE = principalHRAttributesService;
	}

}