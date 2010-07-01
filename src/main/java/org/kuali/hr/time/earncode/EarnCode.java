package org.kuali.hr.time.earncode;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EarnCode extends PersistableBusinessObjectBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long   earnCodeId;
    private String deptId;
    private String tkRuleGroup;
    private String location;
    private String earnCode;
    private boolean active;

    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getTkRuleGroup() {
        return tkRuleGroup;
    }

    public void setTkRuleGroup(String tkRuleGroup) {
        this.tkRuleGroup = tkRuleGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEarnCode() {
        return earnCode;
    }

    public void setEarnCode(String earnCode) {
        this.earnCode = earnCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getEarnCodeId() {
        return earnCodeId;
    }

    public void setEarnCodeId(Long earnCodeId) {
        this.earnCodeId = earnCodeId;
    }

}
