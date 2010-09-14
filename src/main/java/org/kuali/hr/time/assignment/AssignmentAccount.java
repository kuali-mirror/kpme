package org.kuali.hr.time.assignment;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class AssignmentAccount extends PersistableBusinessObjectBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkAssignAcctId;
	private String finCoaCd;
	private String accountNbr;
	private String subAcctNbr;
	private String finObjectCd;
	private String finSubObjCd;
	private String projectCd;
	private String orgRefId;
	private BigDecimal percent;
	private boolean active;
	private Long tkAssignmentId;
	private Assignment assignmentObj;
	
	
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



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		toStringMap.put("finCoaCd", finCoaCd);
		toStringMap.put("accountNbr", accountNbr);
		toStringMap.put("subAcctNbr", subAcctNbr);
		toStringMap.put("finObjectCd", finObjectCd);
		toStringMap.put("finSubObjCd", finSubObjCd);
		toStringMap.put("projectCd", projectCd);
		toStringMap.put("orgRefId", orgRefId);	
		toStringMap.put("projectCd", projectCd);	
		toStringMap.put("percent", percent);	
		return toStringMap;
	}



	public Long getTkAssignAcctId() {
		return tkAssignAcctId;
	}



	public void setTkAssignAcctId(Long tkAssignAcctId) {
		this.tkAssignAcctId = tkAssignAcctId;
	}



	public Long getTkAssignmentId() {
		return tkAssignmentId;
	}



	public void setTkAssignmentId(Long tkAssignmentId) {
		this.tkAssignmentId = tkAssignmentId;
	}
	
}
