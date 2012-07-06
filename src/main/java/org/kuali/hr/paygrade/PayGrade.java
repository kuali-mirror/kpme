package org.kuali.hr.paygrade;

import java.sql.Date;
import java.sql.Timestamp;

import org.kuali.hr.time.HrBusinessObject;

public class PayGrade extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hrPayGradeId;
	private String payGrade;
	private String description;
	private String userPrincipalId;
	private String salGroup;

	public String getHrPayGradeId() {
		return hrPayGradeId;
	}

	public void setHrPayGradeId(String hrPayGradeId) {
		this.hrPayGradeId = hrPayGradeId;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	@Override
	public String getUniqueKey() {
		return payGrade;
	}
	
	@Override
	public String getId() {
		return getHrPayGradeId();
	}

	@Override
	public void setId(String id) {
		setHrPayGradeId(id);
	}

	public String getSalGroup() {
		return salGroup;
	}

	public void setSalGroup(String salGroup) {
		this.salGroup = salGroup;
	}
}
