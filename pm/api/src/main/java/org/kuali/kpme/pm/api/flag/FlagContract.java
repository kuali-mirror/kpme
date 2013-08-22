package org.kuali.kpme.pm.api.flag;


import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>FlagContract interface</p>
 *
 */
public interface FlagContract extends PersistableBusinessObject {
	
	/**
	 * The ID for the Flag object which Classification Flag and pstnFlag extend
	 * 
	 * <p>
	 * pmFlagId of a Flag.
	 * <p>
	 * 
	 * @return pmFlagId for Flag
	 */
	public String getPmFlagId();

	/**
	 * A grouping of flags
	 * 
	 * <p>
	 * category of a Flag
	 * <p>
	 * 
	 * @return category for Flag
	 */
	public String getCategory();

	/**
	 * The name of the Flag
	 * 
	 * <p>
	 * A descriptive name for the flag.
	 * <p>
	 * 
	 * @return names for Flag
	 */
	public String getNames();

}
