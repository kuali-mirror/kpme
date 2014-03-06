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
package org.kuali.kpme.core.assignment.account;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.assignment.account.AssignmentAccountContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.kpme.core.kfs.coa.businessobject.ObjectCode;
import org.kuali.kpme.core.kfs.coa.businessobject.ProjectCode;
import org.kuali.kpme.core.kfs.coa.businessobject.SubAccount;
import org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode;

import com.google.common.collect.ImmutableMap;

public class AssignmentAccount extends HrBusinessObject implements AssignmentAccountContract {

	private static final long serialVersionUID = 2414818440020234952L;
	
	private String tkAssignAcctId;
	private String finCoaCd;
	private String accountNbr;
	private String subAcctNbr;
	private String finObjectCd;
	private String finSubObjCd;
	private String projectCd;
	private String orgRefId;
	private BigDecimal percent;
	private String earnCode;
	private String tkAssignmentId;
	private Assignment assignmentObj;
	
	private Account accountObj;
	private SubAccount subAccountObj;
	private ObjectCode objectCodeObj;
	private SubObjectCode subObjectCodeObj;
	private ProjectCode projectCodeObj;
	private EarnCodeBo earnCodeObj;
	
	// TODO returning an empty map for the time-being, until the BK is finalized
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.build();
	}
	
	public Assignment getAssignmentObj() {
		return assignmentObj;
	}

	public void setAssignmentObj(Assignment assignmentObj) {
		this.assignmentObj = assignmentObj;
	}

	public String getFinCoaCd() {
		return finCoaCd;
	}

	public void setFinCoaCd(String finCoaCd) {
		this.finCoaCd = finCoaCd;
	}

	public String getAccountNbr() {
		return accountNbr;
	}

	public void setAccountNbr(String accountNbr) {
		this.accountNbr = accountNbr;
	}

	public String getSubAcctNbr() {
		return subAcctNbr;
	}

	public void setSubAcctNbr(String subAcctNbr) {
		this.subAcctNbr = subAcctNbr;
	}

	public String getFinObjectCd() {
		return finObjectCd;
	}

	public void setFinObjectCd(String finObjectCd) {
		this.finObjectCd = finObjectCd;
	}

	public String getFinSubObjCd() {
		return finSubObjCd;
	}

	public void setFinSubObjCd(String finSubObjCd) {
		this.finSubObjCd = finSubObjCd;
	}

	public String getProjectCd() {
		return projectCd;
	}

	public void setProjectCd(String projectCd) {
		this.projectCd = projectCd;
	}

	public String getOrgRefId() {
		return orgRefId;
	}

	public void setOrgRefId(String orgRefId) {
		this.orgRefId = orgRefId;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public String getTkAssignAcctId() {
		return tkAssignAcctId;
	}

	public void setTkAssignAcctId(String tkAssignAcctId) {
		this.tkAssignAcctId = tkAssignAcctId;
	}

	public String getTkAssignmentId() {
		return tkAssignmentId;
	}

	public void setTkAssignmentId(String tkAssignmentId) {
		this.tkAssignmentId = tkAssignmentId;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public Account getAccountObj() {
		return accountObj;
	}

	public void setAccountObj(Account accountObj) {
		this.accountObj = accountObj;
	}

	public SubAccount getSubAccountObj() {
		return subAccountObj;
	}

	public void setSubAccountObj(SubAccount subAccountObj) {
		this.subAccountObj = subAccountObj;
	}

	public ObjectCode getObjectCodeObj() {
		return objectCodeObj;
	}

	public void setObjectCodeObj(ObjectCode objectCodeObj) {
		this.objectCodeObj = objectCodeObj;
	}

	public SubObjectCode getSubObjectCodeObj() {
		return subObjectCodeObj;
	}

	public void setSubObjectCodeObj(SubObjectCode subObjectCodeObj) {
		this.subObjectCodeObj = subObjectCodeObj;
	}

	public ProjectCode getProjectCodeObj() {
		return projectCodeObj;
	}

	public void setProjectCodeObj(ProjectCode projectCodeObj) {
		this.projectCodeObj = projectCodeObj;
	}

	public EarnCodeBo getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCodeBo earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

	@Override
	public String getUniqueKey() {
		return earnCode +"_"+accountNbr+"_"+subAcctNbr;
	}

	@Override
	public String getId() {
		return tkAssignAcctId;
	}

	@Override
	public void setId(String id) {
		setTkAssignAcctId(id);
	}
	
}
