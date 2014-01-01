/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.api.time.rules.timecollection;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;

/**
 * <p>TimeCollectionRuleContract interface</p>
 *
 */
public interface TimeCollectionRuleContract extends HrBusinessObjectContract {
	
	/**
	 * The Department object associated with the TimeCollectionRule
	 * 
	 * <p>
	 * departmentObj of a TimeCollectionRule
	 * <p>
	 * 
	 * @return departmentObj for TimeCollectionRule
	 */
	public DepartmentContract getDepartmentObj();

	/**
	 * The pay type associated with the TimeCollectionRule
	 * 
	 * <p>
	 * payType of a TimeCollectionRule
	 * <p>
	 * 
	 * @return payType for TimeCollectionRule
	 */
	public String getPayType();

	/**
	 * The id of the PayType object associated with the TimeCollectionRule
	 * 
	 * <p>
	 * hrPayTypeId of a TimeCollectionRule
	 * <p>
	 * 
	 * @return hrPayTypeId for TimeCollectionRule
	 */
	public String getHrPayTypeId();

	/**
	 * The PayType object associated with the TimeCollectionRule
	 * 
	 * <p>
	 * payTypeObj of a TimeCollectionRule
	 * <p>
	 * 
	 * @return payTypeObj for TimeCollectionRule
	 */
	public PayTypeContract getPayTypeObj();

	/**
	 * The WorkArea object associated with the TimeCollectionRule
	 * 
	 * <p>
	 * workAreaObj of a TimeCollectionRule
	 * <p>
	 * 
	 * @return workAreaObj for TimeCollectionRule
	 */
	public WorkAreaContract getWorkAreaObj();

	/**
	 * The work area number associated with the TimeCollectionRule
	 * 
	 * <p>
	 * workArea of a TimeCollectionRule
	 * <p>
	 * 
	 * @return workArea for TimeCollectionRule
	 */
	public Long getWorkArea();

	/**
	 * The flag to indicate if clock entry will be required for recording time 
	 * based on the department and work area
	 * 
	 * <p>
	 * clockUserFl flag of a TimeCollectionRule
	 * <p>
	 * 
	 * @return Y if required, N if not
	 */
	public boolean isClockUserFl();
	
	/**
	 * The principal Id of user who created the TimeCollectionRule
	 * 
	 * <p>
	 * This field is auto populated.
	 * <p>
	 * 
	 * @return userPrincipalId for TimeCollectionRule
	 */
	public String getUserPrincipalId();

	/**
	 * The primary key of a TimeCollectionRule entry saved in a database
	 * 
	 * <p>
	 * tkTimeCollectionRuleId of a TimeCollectionRule
	 * <p>
	 * 
	 * @return tkTimeCollectionRuleId for TimeCollectionRule
	 */
	public String getTkTimeCollectionRuleId();
	
	/**
	 * The department name associated with the TimeCollectionRule
	 * 
	 * <p>
	 * dept of a TimeCollectionRule
	 * <p>
	 * 
	 * @return dept for TimeCollectionRule
	 */
	public String getDept();
	
	/**
	 * The id of the WorkArea object associated with the TimeCollectionRule
	 * 
	 * <p>
	 * tkWorkAreaId of a TimeCollectionRule
	 * <p>
	 * 
	 * @return tkWorkAreaId for TimeCollectionRule
	 */
	public String getTkWorkAreaId();
	
	/**
	 * The id of the Department object associated with the TimeCollectionRule
	 * 
	 * <p>
	 * hrDeptId of a TimeCollectionRule
	 * <p>
	 * 
	 * @return hrDeptId for TimeCollectionRule
	 */
	public String getHrDeptId();
	
	/**
	 * The history flag of the TimeCollectionRule
	 * 
	 * <p>
	 * history flag of a TimeCollectionRule
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public Boolean getHistory();

}
