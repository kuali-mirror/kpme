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
package org.kuali.kpme.tklm.api.leave.accrual;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.Interval;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.tklm.api.leave.timeoff.SystemScheduledTimeOffContract;

/**
 * <p>RateRangeContract interface</p>
 *
 */
public interface RateRangeContract {

	/**
	 * The date range of the RateRane
	 * 
	 * <p>
	 * range of a RateRange
	 * <p>
	 * 
	 * @return range for RateRange
	 */
	public Interval getRange();

	/**
	 * The list of active jobs associated with the RateRange
	 * 
	 * <p>
	 * jobs of a RateRange
	 * <p>
	 * 
	 * @return jobs for RateRange
	 */
	public List<? extends JobContract> getJobs();

	/**
	 * The fte sum of jobs that are associated with the RateRange
	 * 
	 * <p>
	 * accrualRatePercentageModifier of a RateRange
	 * <p>
	 * 
	 * @return accrualRatePercentageModifier for RateRange
	 */
	public BigDecimal getAccrualRatePercentageModifier();

	/**
	 * The statusChanged flag of the RateRange
	 * 
	 * <p>
	 * statusChanged flag of a RateRange
	 * <p>
	 * 
	 * @return Y if the status has changed, N if not
	 */
	public boolean isStatusChanged();

	/**
	 * The standard hours sum of jobs that are associated with the RateRange
	 * 
	 * <p>
	 * standardHours of a RateRange
	 * <p>
	 * 
	 * @return standardHours for RateRange
	 */
	public BigDecimal getStandardHours();

	/**
	 * The active PrincipalHRAttributes associated with the RateRange
	 * 
	 * <p>
	 * The active principalHRAttributes of jobs that are associated with the RateRange that has max effective date
	 * <p>
	 * 
	 * @return principalHRAttributes for RateRange
	 */
	public PrincipalHRAttributesContract getPrincipalHRAttributes();

	/**
	 * The inactive PrincipalHRAttributes associated with the RateRange
	 * 
	 * <p>
	 * The inactive principalHRAttributes of jobs that are associated with the RateRange that has max effective date
	 * <p>
	 * 
	 * @return endPrincipalHRAttributes for RateRange
	 */
	public PrincipalHRAttributesContract getEndPrincipalHRAttributes();

	/**
	 * The LeavePlan object associated with the RateRange
	 * 
	 * <p>
	 * leavePlan of a RateRange
	 * <p>
	 * 
	 * @return leavePlan for RateRange
	 */
	public LeavePlanContract getLeavePlan();

	/**
	 * The list of AccrualCaegory objects associated with the RateRange
	 * 
	 * <p>
	 * accrualCategories of a RateRange
	 * <p>
	 * 
	 * @return accrualCategories for RateRange
	 */
	public List<? extends AccrualCategoryContract> getAcList();

	/**
	 * The list of AccrualCaegoryRule objects associated with the RateRange
	 * 
	 * <p>
	 * accrualCategoryRules of a RateRange
	 * <p>
	 * 
	 * @return accrualCategoryRules for RateRange
	 */
	public List<? extends AccrualCategoryRuleContract> getAcRuleList();

	/**
	 * The SystemScheduledTimeOff object associated with the RateRange
	 * 
	 * <p>
	 * sysScheTimeOff of a RateRange
	 * <p>
	 * 
	 * @return sysScheTimeOff for RateRange
	 */
	public SystemScheduledTimeOffContract getSysScheTimeOff();

	/**
	 * The leaveCalendarDocumentId associated with the RateRange
	 * 
	 * <p>
	 * leaveCalendarDocumentId of a RateRange, which will be assigned to leave blocks created at this rate range
	 * <p>
	 * 
	 * @return leaveCalendarDocumentId for RateRange
	 */
	public String getLeaveCalendarDocumentId();
	
}
