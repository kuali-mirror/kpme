package org.kuali.kpme.pm.api.classification.qual;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>ClassificationQualificationContract interface</p>
 *
 */
public interface ClassificationQualificationContract extends PersistableBusinessObject {

	/**
	 * The QualificationType associated with the ClassificationQualification
	 * 
	 * <p>
	 * qualificationType of a ClassificationQualification
	 * <p>
	 * 
	 * @return qualificationType for ClassificationQualification
	 */
	public String getQualificationType();

	/**
	 * The Qualifier associated with the ClassificationQualification
	 * 
	 * <p>
	 * qualifier of a ClassificationQualification
	 * <p>
	 * 
	 * @return qualifier for ClassificationQualification
	 */
	public String getQualifier();

	/**
	 * The QualificationValue associated with the ClassificationQualification
	 * 
	 * <p>
	 * qualificationValue of a ClassificationQualification
	 * <p>
	 * 
	 * @return qualificationValue for ClassificationQualification
	 */
	public String getQualificationValue();

	/**
	 * The Id of the Position Class associated with the ClassificationQualification
	 * 
	 * <p>
	 * pmPositionClassId of a ClassificationQualification
	 * <p>
	 * 
	 * @return pmPositionClassId for ClassificationQualification
	 */
	public String getPmPositionClassId();

	/**
	 * The the display order associated with the ClassificationQualification
	 * 
	 * <p>
	 * displayOrder of a ClassificationQualification
	 * <p>
	 * 
	 * @return displayOrder for ClassificationQualification
	 */
	public String getDisplayOrder();
	
	/**
	 * The Primary Key of a ClassificationQualification entry saved in a database
	 * 
	 * <p>
	 * pmClassificationQualificationId of a ClassificationQualification
	 * <p>
	 * 
	 * @return pmClassificationQualificationId for ClassificationQualification
	 */
	public String getPmClassificationQualificationId();
	
	/**
	 * The type value of the ClassificationQualification's getQualificationType() method
	 * 
	 * <p>
	 * typeValue of n ClassificationQualification.getQualificationType
	 * <p>
	 * 
	 * @return typeValue for ClassificationQualification.getQualificationType
	 */
	public String getTypeValue();

}
