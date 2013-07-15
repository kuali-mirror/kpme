package org.kuali.kpme.core.api.bo;

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.LocalDate;
import org.kuali.rice.core.api.mo.common.GloballyUnique;
import org.kuali.rice.core.api.mo.common.Versioned;
import org.kuali.rice.core.api.mo.common.active.Inactivatable;
/**
 * <p>HrBusinessObjectContract interface.</p>
 *
 */
public interface HrBusinessObjectContract extends Versioned, GloballyUnique, Inactivatable{
	
	/**
	 * The unique id defined by the object, could be a combination
	 * of multiple fields
	 * 
	 * <p>
	 * id of HrBusinessObject
	 * <p>
	 * 
	 * @return id of HrBusinessObject
	 */
	public abstract String getId();
	
	/**
	 * The effective date of the HrBusinessObjectContract
	 * 
	 * <p>
	 * effectiveDate of HrBusinessObject
	 * <p>
	 * 
	 * @return effectiveDate of HrBusinessObject
	 */
	public Date getEffectiveDate();
	
	/**
	 * The localDate format of the effective date of the HrBusinessObjectContract
	 * 
	 * <p>
	 * effectiveLocalDate of HrBusinessObject
	 * <p>
	 * 
	 * @return effectiveLocalDate of HrBusinessObject
	 */
	public LocalDate getEffectiveLocalDate();
	
	/**
	 * The timestamp of when this HrBusinessObjectContract was last created/updated
	 * 
	 * <p>
	 * timestamp of HrBusinessObject
	 * <p>
	 * 
	 * @return timestamp of HrBusinessObject
	 */
	public Timestamp getTimestamp();
}
