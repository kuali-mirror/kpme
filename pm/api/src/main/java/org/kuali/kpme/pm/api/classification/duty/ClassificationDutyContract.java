package org.kuali.kpme.pm.api.classification.duty;

import java.math.BigDecimal;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>ClassificationDutyContract interface</p>
 *
 */
public interface ClassificationDutyContract extends PersistableBusinessObject {

	/**
	 * The Primary Key that a ClassificationDuty record will be saved to a database with
	 * 
	 * <p>
	 * pmDutyId of a ClassificationDuty.
	 * <p>
	 * 
	 * @return pmDutyId for ClassificationDuty
	 */
	public String getPmDutyId();

	/**
	 * The short name of the duty associated with the ClassificationDuty
	 * 
	 * <p>
	 * name of a ClassificationDuty.
	 * <p>
	 * 
	 * @return name for ClassificationDuty
	 */
	public String getName();
	
	/**
	 * The text area used for detailed description of the Duty
	 * 
	 * <p>
	 * description of a ClassificationDuty.
	 * <p>
	 * 
	 * @return description for ClassificationDuty
	 */
	public String getDescription();
	
	/**
	 * The percent of time performing the specified duty
	 * 
	 * <p>
	 * percentage of a ClassificationDuty.
	 * <p>
	 * 
	 * @return percentage for ClassificationDuty
	 */
	public BigDecimal getPercentage();
	
	/**
	 * The position class id associated with the ClassificationDuty
	 * 
	 * <p>
	 * pmPositionClassId of a ClassificationDuty.
	 * <p>
	 * 
	 * @return pmPositionClassId for ClassificationDuty
	 */
	public String getPmPositionClassId();

}
