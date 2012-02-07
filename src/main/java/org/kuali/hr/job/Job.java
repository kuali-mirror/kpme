package org.kuali.hr.job;

import org.kuali.hr.location.Location;
import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
/**
 * 
 * Job representation
 *
 */
public class Job extends HrBusinessObject {

	/*
	 * Standard field included for serialization support
	 */
	private static final long serialVersionUID = 1L;
	
	private String location;
	private String hrPayType;
	private String payGrade;
	private BigDecimal standardHours;
	private String hrJobId;
	private String principalId;
	private String firstName;
	private String lastName;
	private String principalName;
	private Long jobNumber;
	private String dept;
	private String hrSalGroup;
	private Boolean primaryIndicator;
	private Boolean history;
	private BigDecimal compRate = new BigDecimal(0);
	private String positionNumber;
	
	private String hrDeptId;
	private String hrPayTypeId;
	private boolean eligibleForLeave;
	
	private Person principal;
	private Department deptObj;
	private PayType payTypeObj;
	private Location locationObj;
    private PayGrade payGradeObj;
    private SalGroup salGroupObj;
    private Position positionObj;

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		toStringMap.put("jobId", hrJobId);
		toStringMap.put("principalId", principalId);
		toStringMap.put("hrSalGroup", hrSalGroup);

		return toStringMap;
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
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		if (principal == null) {
            principal = KIMServiceLocator.getPersonService().getPerson(this.principalId);
	    }
	    return (principal != null) ? principal.getName() : "";
	}

	public String getPrincipalName() {
		if(principalName == null && !this.getPrincipalId().isEmpty()) {
			Person aPerson = KIMServiceLocator.getPersonService().getPerson(getPrincipalId());
			setPrincipalName(aPerson.getName());
		}
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
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

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
	
	public String getHrPayType() {
		return hrPayType;
	}

	public void setHrPayType(String hrPayType) {
		this.hrPayType = hrPayType;
	}

	public String getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(String hrJobId) {
		this.hrJobId = hrJobId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getHrSalGroup() {
		return hrSalGroup;
	}

	public void setHrSalGroup(String hrSalGroup) {
		this.hrSalGroup = hrSalGroup;
	}


	public BigDecimal getCompRate() {
		return compRate;
	}


	public void setCompRate(BigDecimal compRate) {
		if(compRate != null){
			this.compRate = compRate.setScale(TkConstants.BIG_DECIMAL_SCALE);
		} else {
			this.compRate = compRate;
		}
	}

	public Department getDeptObj() {
		return deptObj;
	}


	public void setDeptObj(Department deptObj) {
		this.deptObj = deptObj;
	}


	public PayType getPayTypeObj() {
		return payTypeObj;
	}


	public void setPayTypeObj(PayType payTypeObj) {
		this.payTypeObj = payTypeObj;
	}


	public Person getPrincipal() {
		return principal;
	}


	public void setPrincipal(Person principal) {
		this.principal = principal;
	}


	public void setPrimaryIndicator(Boolean primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}


	public Boolean getPrimaryIndicator() {
		return primaryIndicator;
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	public PayGrade getPayGradeObj() {
		return payGradeObj;
	}

	public void setPayGradeObj(PayGrade payGradeObj) {
		this.payGradeObj = payGradeObj;
	}

	public SalGroup getSalGroupObj() {
		return salGroupObj;
	}

	public void setSalGroupObj(SalGroup salGroupObj) {
		this.salGroupObj = salGroupObj;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionObj(Position positionObj) {
		this.positionObj = positionObj;
	}

	public Position getPositionObj() {
		return positionObj;
	}

	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	public String getHrPayTypeId() {
		return hrPayTypeId;
	}

	public void setHrPayTypeId(String hrPayTypeId) {
		this.hrPayTypeId = hrPayTypeId;
	}

	@Override
	public String getUniqueKey() {
		return getPrincipalId() + "_" + getJobNumber();
	}

	@Override
	public String getId() {
		return getHrJobId();
	}

	@Override
	public void setId(String id) {
		setHrJobId(id);
	}
	public boolean isEligibleForLeave() {
		return eligibleForLeave;
	}

	public void setEligibleForLeave(boolean eligibleForLeave) {
		this.eligibleForLeave = eligibleForLeave;
	}
}
