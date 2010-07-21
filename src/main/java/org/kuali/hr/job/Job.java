package org.kuali.hr.job;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class Job  extends PersistableBusinessObjectBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long jobId;
    private String principalId;
    private Long jobNumber;
    private Date effectiveDate;
    private String deptId;
    private Long payCalendarId;
    private String tkRuleGroup;
    private Timestamp timestamp;
    private Boolean active;
    
    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
	LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();
	
	toStringMap.put("principalId", principalId);
	
	return toStringMap;
    }


    public Long getJobId() {
        return jobId;
    }


    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
    

    public Long getPayCalendarId() {
        return payCalendarId;
    }


    public void setPayCalendarId(Long payCalendarId) {
        this.payCalendarId = payCalendarId;
    }


    public String getTkRuleGroup() {
        return tkRuleGroup;
    }


    public void setTkRuleGroup(String tkRuleGroup) {
        this.tkRuleGroup = tkRuleGroup;
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

}
