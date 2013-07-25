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
package org.kuali.kpme.core.api.assignment.account;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.kfs.coa.businessobject.AccountContract;
import org.kuali.kpme.core.api.kfs.coa.businessobject.ObjectCodeContract;
import org.kuali.kpme.core.api.kfs.coa.businessobject.ProjectCodeContract;
import org.kuali.kpme.core.api.kfs.coa.businessobject.SubAccountContract;
import org.kuali.kpme.core.api.kfs.coa.businessobject.SubObjectCodeContract;
import org.kuali.rice.core.api.mo.common.GloballyUnique;
import org.kuali.rice.core.api.mo.common.Versioned;
import org.kuali.rice.core.api.mo.common.active.Inactivatable;

/**
 * <p>AssignmentAccountContract interface.</p>
 *
 */
public interface AssignmentAccountContract extends HrBusinessObjectContract {
	
	/**
	 * Assignment object this AssignmentAccoutn is associated with
	 *  
	 * <p>
	 * assignmentObj of AssignmentAccount
	 * <p>
	 * 
	 * @return assignmentObj for AssignmentAccount
	 */
	public AssignmentContract getAssignmentObj();
	
	/**
	 *  Chart component of the chart of accounts (COA) to be charged when time recorded against this work area and task. 
	 * 
	 * <p>
	 * finCoaCd of AssignmentAccount
	 * <p>
	 * 
	 * @return finCoaCd for AssignmentAccount
	 */
	public String getFinCoaCd();

	/**
	 * Account component of the chart of accounts to be charged when time recorded against 
	 * this work area and task. An account for any department can be entered and is not limited
	 * to only accounts associated with the job department. 
	 * 
	 * <p>
	 * accountNbr of AssignmentAccount
	 * <p>
	 * 
	 * @return accountNbr for AssignmentAccount
	 */
	public String getAccountNbr();

	/**
	 * Optional - sub-account component of the chart of accounts to be charged when time recorded 
	 * against this work area and task. Sub-account value must be defined for the account selected
	 * to be available for entry. 
	 * 
	 * <p>
	 * subAccountNbr of AssignmentAccount
	 * <p>
	 * 
	 * @return subAccountNbr for AssignmentAccount
	 */
	public String getSubAcctNbr();

	/**
	 * Object code component of the chart of accounts to be charged when time recorded against
	 * this work area and task.  
	 * 
	 * <p>
	 * finObjectCd of AssignmentAccount
	 * <p>
	 * 
	 * @return finObjectCd for AssignmentAccount
	 */
	public String getFinObjectCd();
	
	/**
	 * Optional - sub-object component of the chart of accounts to be charged when time recorded 
	 * against this work area and task. Sub-object must be associated with the object code.   
	 * 
	 * <p>
	 * finSubObjCd of AssignmentAccount
	 * <p>
	 * 
	 * @return finSubObjCd for AssignmentAccount
	 */
	public String getFinSubObjCd();
	
	/**
	 * Optional project code component of the chart of accounts to be charged when time recorded
	 * against this work area and task, must be established on the project table. 
	 * 
	 * <p>
	 * projectCd of AssignmentAccount
	 * <p>
	 * 
	 * @return projectCd for AssignmentAccount
	 */
	public String getProjectCd();
	
	/**
	 * Optional funding attribute, free form entry field 
	 * 
	 * <p>
	 * orgRefId of AssignmentAccount
	 * <p>
	 * 
	 * @return orgRefId for AssignmentAccount
	 */
	public String getOrgRefId();
	
	/**
	 * Percent of earnings recorded against assignment to be paid by the defined account. 
	 * Total of accounts for an earn code must be 100%
	 * 
	 * <p>
	 * percent of AssignmentAccount
	 * <p>
	 * 
	 * @return percent for AssignmentAccount
	 */
	public BigDecimal getPercent();
	
	/**
	 * The Primary Key of an AssignmentAccount entry saved in a database
	 * 
	 * <p>
	 * tkAssignAcctId of AssignmentAccount
	 * <p>
	 * 
	 * @return tkAssignAcctId of AssignmentAccount
	 */
	public String getTkAssignAcctId();

	/**
	 * Id of the Assignment object associated with this AssignmentAccount
	 * 
	 * <p>
	 * tkAssignmentId of AssignmentAccount
	 * <p>
	 * 
	 * @return tkAssignmentId of AssignmentAccount
	 */
	public String getTkAssignmentId();

	/**
	 * The earn code for which earnings will be charged to the account identified. 
	 * Funding must be defined for the job’s regular pay earn code. If an earn code’s funding 
	 * is different from the regular earn code, define it here. Anything not defined will 
	 * follow the regular earn code’s funding. 
	 * 
	 * <p>
	 * earnCode of AssignmentAccount
	 * <p>
	 * 
	 * @return earnCode of AssignmentAccount
	 */
	public String getEarnCode();

	/**
	 * Account component of the chart of accounts to be charged when time recorded against 
	 * this work area and task. An account for any department can be entered and is not limited
	 * to only accounts associated with the job department. 
	 * 
	 * <p>
	 * accountObj of AssignmentAccount
	 * <p>
	 * 
	 * @return accountObj for AssignmentAccount
	 */
	public AccountContract getAccountObj();
	
	/**
	 * Optional - sub-account component of the chart of accounts to be charged when time recorded 
	 * against this work area and task. Sub-account value must be defined for the account selected
	 * to be available for entry. 
	 * 
	 * <p>
	 * subAccountObj of AssignmentAccount
	 * <p>
	 * 
	 * @return subAccountObj for AssignmentAccount
	 */
	public SubAccountContract getSubAccountObj();	

	/**
	 * Object code component of the chart of accounts to be charged when time recorded against
	 * this work area and task.  
	 * 
	 * <p>
	 * objectCodeObj of AssignmentAccount
	 * <p>
	 * 
	 * @return objectCodeObj for AssignmentAccount
	 */
	public ObjectCodeContract getObjectCodeObj();
	
	/**
	 * Optional - sub-object component of the chart of accounts to be charged when time recorded 
	 * against this work area and task. Sub-object must be associated with the object code.   
	 * 
	 * <p>
	 * subObjectCodeObj of AssignmentAccount
	 * <p>
	 * 
	 * @return subObjectCodeObj for AssignmentAccount
	 */
	public SubObjectCodeContract getSubObjectCodeObj();
	
	/**
	 * Optional project code component of the chart of accounts to be charged when time recorded
	 * against this work area and task, must be established on the project table. 
	 * 
	 * <p>
	 * projectCodeObj of AssignmentAccount
	 * <p>
	 * 
	 * @return projectCodeObj for AssignmentAccount
	 */
	public ProjectCodeContract getProjectCodeObj();
	
	/**
	 * The earn code for which earnings will be charged to the account identified. 
	 * Funding must be defined for the job’s regular pay earn code. If an earn code’s funding 
	 * is different from the regular earn code, define it here. Anything not defined will 
	 * follow the regular earn code’s funding. 
	 * 
	 * <p>
	 * earnCodeObj of AssignmentAccount
	 * <p>
	 * 
	 * @return earnCodeObj of AssignmentAccount
	 */
	public EarnCodeContract getEarnCodeObj();
}
