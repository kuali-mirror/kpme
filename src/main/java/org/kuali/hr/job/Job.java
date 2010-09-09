package org.kuali.hr.job;

import java.math.BigDecimal;
import java.math.BigInteger;
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
	private Long payTypeId;
	private Boolean fte;
	private String payGrade;
	private BigDecimal standardHours;
	private Long jobId;
	private String principalId;
	private Long jobNumber;
	private Date effectiveDate;
	private String deptId;
	private Long tkSalGroupId;
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

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getPayTypeId() {
		return payTypeId;
	}

	public void setPayTypeId(Long payTypeId) {
		this.payTypeId = payTypeId;
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

	public Long getTkSalGroupId() {
		return tkSalGroupId;
	}

	public void setTkSalGroup(Long tkSalGroupId) {
		this.tkSalGroupId = tkSalGroupId;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

}
