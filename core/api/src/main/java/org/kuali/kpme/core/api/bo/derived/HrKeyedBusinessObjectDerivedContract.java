package org.kuali.kpme.core.api.bo.derived;

import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.mo.KPMEEffectiveKeyedDerivedDataTransferObject;

public interface HrKeyedBusinessObjectDerivedContract extends HrBusinessObjectDerivedContract, KPMEEffectiveKeyedDerivedDataTransferObject{
	
	/**
     * The Location object associated with the keyed business object
     *
     *
     * @return locationObj for this keyed business object
     */
	public LocationContract getLocationObj();

    /**
     * The Institution object associated with the keyed business object
     *
     *
     * @return institutionObj for this keyed business object
     */
	public InstitutionContract getInstitutionObj();
	
	/**
     * The Institution code associated with the keyed business object
     *
     *
     * @return institution code for this keyed business object
     */
	public String getInstitution();

	/**
     * The Location code associated with the keyed business object
     *
     *
     * @return location code for this keyed business object
     */
	public String getLocation();
}
