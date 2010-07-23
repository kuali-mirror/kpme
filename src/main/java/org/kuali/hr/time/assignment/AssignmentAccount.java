package org.kuali.hr.time.assignment;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class AssignmentAccount extends PersistableBusinessObjectBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long assignAcctId;
	private String finCoaCd;
	private String accountNbr;
	private String subAcctNbr;
	private String finObjectCd;
	private String finSubObjCd;
	private String projectCd;
	private String orgRefId;
	private BigDecimal percent;
	private boolean active;
	private Long assignmentId;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}



	public Long getAssignAcctId() {
		return assignAcctId;
	}



	public void setAssignAcctId(Long assignAcctId) {
		this.assignAcctId = assignAcctId;
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



	public Long getAssignmentId() {
		return assignmentId;
	}



	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

}
