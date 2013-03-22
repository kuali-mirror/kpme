package org.kuali.hr.pm.paystep;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.pm.institution.Institution;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.krad.bo.InactivatableFromToImpl;
import org.kuali.rice.krad.bo.KualiCode;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.location.api.campus.Campus;

public class PayStep extends HrBusinessObject implements Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String payStepId;
	private String payStep;
	private String institution;
	private String campus;
	private String salaryGroup;
	private String payGrade;
	private int stepNumber;
	private BigDecimal compRate;
	private int serviceInterval;
	private String serviceUnit;
	
	private Institution instutitionObj;
	private Campus campusObj;
	//private SalaryGroup SalaryGroupObj;
	//private PayGrade PayGradeObj;

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return super.isActive();
	}

	@Override
	public void setActive(boolean active) {
		// TODO Auto-generated method stub
		super.setActive(active);
	}

	@Override
	public String getObjectId() {
		// TODO Auto-generated method stub
		return super.getObjectId();
	}

	@Override
	public Long getVersionNumber() {
		// TODO Auto-generated method stub
		return super.getVersionNumber();
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof PayStep) {
			PayStep s = (PayStep) o;
			if(StringUtils.equals(s.salaryGroup,salaryGroup)
					&& StringUtils.equals(s.payGrade,payGrade)) {

				Integer otherServiceTime = 0;
				if(StringUtils.equals(s.serviceUnit,LMConstants.SERVICE_TIME_YEAR))
					otherServiceTime = s.getServiceInterval() * 12;
				else
					otherServiceTime = s.getServiceInterval();
				
				Integer thisServiceTime = 0;
				if(StringUtils.equals(serviceUnit, LMConstants.SERVICE_TIME_YEAR))
					thisServiceTime = serviceInterval * 12;
				else
					thisServiceTime = serviceInterval;
				
				return otherServiceTime.compareTo(thisServiceTime);
			}
			else
				throw new IllegalArgumentException("pay step must be within the same salary group and pay grade");
		}
			
		return 0;
	}

	public String getPayStep() {
		return payStep;
	}

	public void setPayStep(String payStep) {
		this.payStep = payStep;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getSalaryGroup() {
		return salaryGroup;
	}

	public void setSalaryGroup(String salaryGroup) {
		this.salaryGroup = salaryGroup;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public BigDecimal getCompRate() {
		return compRate;
	}

	public void setCompRate(BigDecimal compRate) {
		this.compRate = compRate;
	}

	public int getServiceInterval() {
		return serviceInterval;
	}

	public void setServiceInterval(int serviceInterval) {
		this.serviceInterval = serviceInterval;
	}

	public String getServiceUnit() {
		return serviceUnit;
	}

	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

	@Override
	public String getId() {
		return payStepId;
	}

	@Override
	public void setId(String id) {
		payStepId = id;
	}

	@Override
	protected String getUniqueKey() {
		return getPayStepId();
	}

	public String getPayStepId() {
		return payStepId;
	}

	public void setPayStepId(String payStepId) {
		this.payStepId = payStepId;
	}

	public Institution getInstutitionObj() {
		return instutitionObj;
	}

	public void setInstutitionObj(Institution instutitionObj) {
		this.instutitionObj = instutitionObj;
	}

	public Campus getCampusObj() {
		return campusObj;
	}

	public void setCampusObj(Campus campusObj) {
		this.campusObj = campusObj;
	}

}
