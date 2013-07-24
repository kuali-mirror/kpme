package org.kuali.kpme.core.api.institution;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>InstitutionContract interface.</p>
 *
 */
public interface InstitutionContract extends HrBusinessObjectContract {
	
	/**
	 * Text that identifies this Institution
	 * 
	 * <p>
	 * institutionCode of Institution
	 * </p>
	 * 
	 * @return institutionCode for Institution
	 */
	public String getInstitutionCode();
	
	/**
	 * Text that describes this Institution
	 * 
	 * <p>
	 * description of Institution
	 * </p>
	 * 
	 * @return description for Institution
	 */
	public String getDescription();
	
	/**
	 * Primary key of Institution object associated with this Assignment
	 * 
	 * <p>
	 * pmInstitutionId of Institution
	 * </p>
	 * 
	 * @return pmInstitutionId for Institution
	 */
	public String getPmInstitutionId();
}
