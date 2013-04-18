package org.kuali.hr.pm.pstnqlfctnvl;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class PositionQualificationValue extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = 1L;

	private String pmPstnQlfctnVlId;
	private String vlName;
	
	public String getVlName() {
		return vlName;
	}
	public void setVlName(String vlName) {
		this.vlName = vlName;
	}
	public String getPmPstnQlfctnVlId() {
		return pmPstnQlfctnVlId;
	}
	public void setPmPstnQlfctnVlId(String pmPstnQlfctnVlId) {
		this.pmPstnQlfctnVlId = pmPstnQlfctnVlId;
	}
	
	
	
}
