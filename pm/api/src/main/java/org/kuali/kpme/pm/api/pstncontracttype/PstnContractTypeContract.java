package org.kuali.kpme.pm.api.pstncontracttype;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.api.location.LocationContract;

/**
 * <p>PstnContractTypeContract interface</p>
 *
 */
public interface PstnContractTypeContract extends HrBusinessObjectContract {

	/**
	 * The primary key of a PstnContractType entry saved in a database
	 * 
	 * <p>
	 * pmCntrctTypeId of a PstnContractType.
	 * <p>
	 * 
	 * return pmCntrctTypeId for PstnContractType
	 */
	public String getPmCntrctTypeId();

	/**
	 * The short text descriptive of the PstnContractType
	 * 
	 * <p>
	 * name of a PstnContractType.
	 * <p>
	 * 
	 * return name for PstnContractType
	 */
	public String getName();

	/**
	 * The long description of the PstnContractType
	 * 
	 * <p>
	 * description of a PstnContractType.
	 * <p>
	 * 
	 * return description for PstnContractType
	 */
	public String getDescription();

	/**
	 * The institution associated with the PstnContractType
	 * 
	 * <p>
	 * institution of a PstnContractType.
	 * <p>
	 * 
	 * return institution for PstnContractType
	 */
	public String getInstitution();

	/**
	 * The location associated with the PstnContractType
	 * 
	 * <p>
	 * location of a PstnContractType.
	 * <p>
	 * 
	 * return location for PstnContractType
	 */
	public String getLocation();

	/**
	 * The Location object associated with the PstnContractType
	 * 
	 * <p>
	 * location of a PstnContractType.
	 * <p>
	 * 
	 * return location for PstnContractType
	 */
	public LocationContract getLocationObj();

	/**
	 * The Institution object associated with the PstnContractType
	 * 
	 * <p>
	 * institution of a PstnContractType.
	 * <p>
	 * 
	 * return institution object for PstnContractType
	 */
	public InstitutionContract getInstitutionObj();

}
