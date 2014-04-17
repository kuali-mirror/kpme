package org.kuali.kpme.core.bo.derived;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.bo.derived.HrKeyedBusinessObjectDerivedContract;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.institution.InstitutionBo;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.service.HrServiceLocator;

public abstract class HrKeyedBusinessObjectDerived extends HrBusinessObjectDerived implements HrKeyedBusinessObjectDerivedContract {

	private static final long serialVersionUID = -4676316817255855400L;
	
	protected String groupKeyCode;
	protected transient HrGroupKeyBo groupKey;
	
	@Override
	public String getGroupKeyCode() {
		return groupKeyCode;
	}

	public void setGroupKeyCode(String groupKeyCode) {
		this.groupKeyCode = groupKeyCode;
	}

	@Override
	public HrGroupKeyBo getGroupKey() {
		
		LocalDate asOfDate = LocalDate.now();
		if(this.getEffectiveLocalDateOfOwner() != null) {
			asOfDate = this.getEffectiveLocalDateOfOwner();
		}
		
		if (groupKey == null) {
			groupKey = HrGroupKeyBo.from(HrServiceLocator.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(), asOfDate));
		}
		
		return groupKey;
	}

	public void setGroupKey(HrGroupKeyBo groupKey) {
		this.groupKey = groupKey;
	}
	
	
	/**
	 * @return the locationObj
	 */
	public LocationBo getLocationObj() {
		HrGroupKeyBo grpKey = getGroupKey(); 
		if(grpKey != null) {
			return grpKey.getLocation();
		}
		return null;		
	}

	
	/**
	 * @return the institutionObj
	 */
	public InstitutionBo getInstitutionObj() {
		HrGroupKeyBo grpKey = getGroupKey(); 
		if(grpKey != null) {
			return grpKey.getInstitution();
		}
		return null;
	}
	
	/**
	 * @return the institutionCode
	 */
	public String getInstitution() {
		HrGroupKeyBo grpKey = getGroupKey(); 
		if(grpKey != null) {
			return grpKey.getInstitutionCode();
		}
		return null;
	}


	/**
	 * @return the locationId
	 */
	public String getLocation() {
		
		HrGroupKeyBo grpKey = getGroupKey(); 
		if(grpKey != null) {
			return grpKey.getLocationId();
		}
		return null;
	}

}
