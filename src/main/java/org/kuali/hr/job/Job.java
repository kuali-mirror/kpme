package org.kuali.hr.job;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class Job extends PersistableBusinessObjectBase {

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	private String location;
	private String hrPayType;
	private Boolean fte;
	private String payGrade;
	private BigDecimal standardHours;
	private Long hrJobId;
	private String principalId;
	private Long jobNumber;
	private Date effectiveDate;
	private String dept;
	private String tkSalGroup;
	private Timestamp timestamp;
	private Boolean active;
	private PayType payType;
	private List<Assignment> assignments;
	private List<DepartmentEarnCode> deptEarnCodes;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();

		toStringMap.put("principalId", principalId);

		return toStringMap;
	}


	public Boolean getFte() {
		return fte;
	}

	public void setFte(Boolean fte) {
		this.fte = fte;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public BigDecimal getStandardHours() {
		return standardHours;
	}

	public void setStandardHours(BigDecimal standardHours) {
		this.standardHours = standardHours;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public Long getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public List<DepartmentEarnCode> getDeptEarnCodes() {
		return deptEarnCodes;
	}

	public void setDeptEarnCodes(List<DepartmentEarnCode> deptEarnCodes) {
		this.deptEarnCodes = deptEarnCodes;
	}

	public String getHrPayType() {
		return hrPayType;
	}

	public void setHrPayType(String hrPayType) {
		this.hrPayType = hrPayType;
	}

	public Long getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(Long hrJobId) {
		this.hrJobId = hrJobId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTkSalGroup() {
		return tkSalGroup;
	}

	public void setTkSalGroup(String tkSalGroup) {
		this.tkSalGroup = tkSalGroup;
	}

}
