package org.kuali.kpme.pm.api.pstnqlfrtype;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>PstnQlfrTypeContract interface</p>
 *
 */
public interface PstnQlfrTypeContract extends HrBusinessObjectContract {

	/**
	 * The primary key for a PstnQlfrType entry saved in the database
	 * 
	 * <p>
	 * pmPstnQlfrTypeId of a PstnQlfrType
	 * <p>
	 * 
	 * @return PstnQlfrType for PstnQlfrType
	 */
	public String getPmPstnQlfrTypeId();

	/**
	 * The id of the PstnQlfrType
	 * 
	 * <p>
	 * code of a PstnQlfrType
	 * <p>
	 * 
	 * @return code for PstnQlfrType
	 */
	public String getCode();
	
	/**
	 * The shortened name for the qualifier type that will be used to select the qualifier
	 * 
	 * <p>
	 * type of a PstnQlfrType
	 * <p>
	 * 
	 * @return type for PstnQlfrType
	 */
	public String getType();
	
	/**
	 * The comma delimited list of qualification values that user can select 
	 * 
	 * <p>
	 * selectValues of a PstnQlfrType
	 * <p>
	 * 
	 * @return selectValues for PstnQlfrType
	 */
	public String getSelectValues();

	/**
	 * The type value associated with the PstnQlfrType
	 * 
	 * <p>
	 * typeValue of a PstnQlfrType
	 * <p>
	 * 
	 * @return typeValue for PstnQlfrType
	 */
	public String getTypeValue();
	
	/**
	 * The description of the PstnQlfrType
	 * 
	 * <p>
	 * descr of a PstnQlfrType
	 * <p>
	 * 
	 * @return descr for PstnQlfrType
	 */
	public String getDescr();

}
