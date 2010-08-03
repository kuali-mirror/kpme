package org.kuali.hr.time.dept.lunch;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class DeptLunchRule extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;

    private Long deptLunchRuleId;
    private String deptId;
    private BigDecimal workArea;
    private String principalId;         // like principal id
    private BigInteger jobNumber;    // like job number
    private Date effectiveDate;
    private boolean active;
    private String requiredClockFl;
    private BigDecimal maxMins;
    private String userPrincipalId;

    private Timestamp timestamp;


    @Override
    protected LinkedHashMap toStringMapper() {
	// TODO Auto-generated method stub
	LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();
		toStringMap.put("principalId", principalId);
		return toStringMap;
    }


    public Long getDeptLunchRuleId() {
        return deptLunchRuleId;
    }


    public void setDeptLunchRuleId(Long deptLunchRuleId) {
        this.deptLunchRuleId = deptLunchRuleId;
    }

    public BigDecimal getWorkArea() {
        return workArea;
    }


    public void setWorkArea(BigDecimal workArea) {
        this.workArea = workArea;
    }


    public Date getEffectiveDate() {
        return effectiveDate;
    }


    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    public String getRequiredClockFl() {
        return requiredClockFl;
    }


    public void setRequiredClockFl(String requiredClockFl) {
        this.requiredClockFl = requiredClockFl;
    }



    public BigDecimal getMaxMins() {
        return maxMins;
    }


    public void setMaxMins(BigDecimal maxMins) {
        this.maxMins = maxMins;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public String getPrincipalId() {
        return principalId;
    }


    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }


    public BigInteger getJobNumber() {
        return jobNumber;
    }


    public void setJobNumber(BigInteger jobNumber) {
        this.jobNumber = jobNumber;
    }


    public String getUserPrincipalId() {
        return userPrincipalId;
    }


    public void setUserPrincipalId(String userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }


    public String getDeptId() {
        return deptId;
    }


    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
