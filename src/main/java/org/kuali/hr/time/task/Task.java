package org.kuali.hr.time.task;

import java.math.BigInteger;
import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class Task extends PersistableBusinessObjectBase {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BigInteger taskId;
    private BigInteger workAreaId;
    private String description;
    private String departmentId;
    private String userPrincipalId;
    private Date effectiveDate;
    private String administrativeDescription;

    public BigInteger getTaskId()
    {
    	return taskId;
    }

    public void setTaskId(BigInteger taskId)
    {
    	this.taskId = taskId;
    }

    public BigInteger getWorkAreaId()
    {
    	return workAreaId;
    }

    public void setWorkAreaId(BigInteger workAreaId){
    	this.workAreaId = workAreaId;
    }

    public String getDescription()
    {
    return description;
    }

    public void setDescription(String desc)
    {
    	this.description = desc;
    }

    public String getDepartmentId()
    {
    	return departmentId;
    }

    public void setDepartmentId(String deptId){
    	this.departmentId = deptId;
    }

    public String getUserPrincipalId(){
    	return userPrincipalId;
    }

    public void setUserPrincipalId(String pid)
    {
    	this.userPrincipalId = pid;
    }

    public Date getEffectiveDate(){
    	return effectiveDate;
    }

    public void setEffectiveDate(Date ed)
    {
    	this.effectiveDate = ed;
    }

    public String getAdministrativeDescription(){
    	return administrativeDescription;
    }

    public void setAdministrativeDescription(String addesc)
    {
    	this.administrativeDescription = addesc;
    }

	@Override
	protected LinkedHashMap toStringMapper() {

		return null;
	}


}
