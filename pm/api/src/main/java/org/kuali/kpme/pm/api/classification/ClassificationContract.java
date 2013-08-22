package org.kuali.kpme.pm.api.classification;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.pm.api.classification.duty.ClassificationDutyContract;
import org.kuali.kpme.pm.api.classification.flag.ClassificationFlagContract;
import org.kuali.kpme.pm.api.classification.qual.ClassificationQualificationContract;

/**
 * <p>ClassificationContract interface</p>
 *
 */
public interface ClassificationContract extends HrBusinessObjectContract {

	/**
	 * The identifier of the position classification
	 * 
	 * <p>
	 * positionClass of the Classification.
	 * <p>
	 * 
	 * @return positionClass for Classification
	 */
	public String getPositionClass();

	/**
	 * The text description for the position classification
	 * 
	 * <p>
	 * classificationTitle for the Classification.
	 * <p>
	 * 
	 * @return classificationTitle for Classification
	 */
	public String getClassificationTitle();
	
	/**
	 * The institution associated with the Classification
	 * 
	 * <p>
	 * institution for the Classification.
	 * <p>
	 * 
	 * @return institution for Classification
	 */
	public String getInstitution();

	/**
	 * The location associated with the Classification
	 * 
	 * <p>
	 * location for the Classification.
	 * <p>
	 * 
	 * @return location for Classification
	 */
	public String getLocation();

	/**
	 * The grouping of Salary Group payroll attributes to be assigned to Position Classification
	 * 
	 * <p>
	 * salaryGroup for the Classification.
	 * <p>
	 * 
	 * @return salaryGroup for Classification
	 */
	public String getSalaryGroup();

	/**
	 * The maximum percentage of time worked for the position classification
	 * 
	 * <p>
	 * percentTime of the Classification.
	 * <p>
	 * 
	 * @return percentTime for Classification
	 */
	public BigDecimal getPercentTime();

	/**
	 * The flag indicating if the position classification is eligible for benefits
	 * 
	 * <p>
	 * benefitsEligible of the Classification.
	 * <p>
	 * 
	 * @return benefitsEligible for Classification
	 */
	public String getBenefitsEligible();

	/**
	 * The flag indicating if the position classification is eligible for leave benefits
	 * 
	 * <p>
	 * leaveEligible of the Classification.
	 * <p>
	 * 
	 * @return leaveEligible for Classification
	 */
	public String getLeaveEligible();

	/**
	 * The leavePlan associated with the salary group 
	 * 
	 * <p>
	 * leavePlan of the Classification.
	 * <p>
	 * 
	 * @return leavePlan for Classification
	 */
	public String getLeavePlan();

	/**
	 * The high level grouping of Position Reporting Group Sub Categories that can be assigned to a Position Classification
	 * 
	 * <p>
	 * positionReportGroup of the Classification.
	 * <p>
	 * 
	 * @return positionReportGroup for Classification
	 */
	public String getPositionReportGroup();

	/**
	 * The grouping of Position Classification to determine routing of action reason for position maintenance
	 * 
	 * <p>
	 * positionType of the Classification.
	 * <p>
	 * 
	 * @return positionType for Classification
	 */
	public String getPositionType();
	
	/**
	 * The flag indicating if the position classification is eligible to be used for pooled position
	 * 
	 * <p>
	 * poolEligible for the Classification.
	 * <p>
	 * 
	 * @return poolEligible for Classification
	 */
	public String getPoolEligible();

	/**
	 * The flag indicating if the position classification is eligible for tenure
	 * 
	 * <p>
	 * tenureEligible for a Classification.
	 * <p>
	 * 
	 * @return tenureEligible for Classification
	 */
	public String getTenureEligible();

	/**
	 * The optional reference field used to enter a url link with detailed description of the Position Classification
	 * 
	 * <p>
	 * externalReference for the Classification.
	 * <p>
	 * 
	 * @return externalReference for Classification
	 */
	public String getExternalReference();

	/**
	 * The list of ClassificationQualification objects associated with the Classification
	 * 
	 * <p>
	 * qualificationList for the Classification.
	 * <p>
	 * 
	 * @return qualificationList for Classification
	 */
	public List<? extends ClassificationQualificationContract> getQualificationList();

	/**
	 * The primary key for a Classification entry saved in the database
	 * 
	 * <p>
	 * pmPositionClassId for the Classification.
	 * <p>
	 * 
	 * @return pmPositionClassId for Classification
	 */
	public String getPmPositionClassId();

	/**
	 * The Location Object associated with the Classification
	 * 
	 * <p>
	 * Location object for the Classification.
	 * <p>
	 * 
	 * @return locationObj for Classification
	 */
	public LocationContract getLocationObj();

	/**
	 * The list of ClassificationDuty Objects associated with the Classification
	 * 
	 * <p>
	 * List of ClassificationDuty Objects for the Classification.
	 * <p>
	 * 
	 * @return dutyList for Classification
	 */
	public List<? extends ClassificationDutyContract> getDutyList();

	/**
	 * The list of ClassificationFlag Objects associated with the Classification
	 * 
	 * <p>
	 * List of ClassificationFlag Objects for the Classification.
	 * <p>
	 * 
	 * @return flagList for Classification
	 */
	public List<? extends ClassificationFlagContract> getFlagList();

}
