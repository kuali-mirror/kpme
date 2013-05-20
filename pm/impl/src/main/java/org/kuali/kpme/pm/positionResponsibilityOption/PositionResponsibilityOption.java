package org.kuali.kpme.pm.positionResponsibilityOption;

import org.kuali.kpme.core.bo.HrBusinessObject;

public class PositionResponsibilityOption extends HrBusinessObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5054782543015429750L;
	
	private String prOptionId;
	private String prOptionName;
	private String prDescription;
	
	
	public String getPrOptionId() {
		return prOptionId;
	}

	public void setPrOptionId(String prOptionId) {
		this.prOptionId = prOptionId;
	}

	public String getPrOptionName() {
		return prOptionName;
	}

	public void setPrOptionName(String prOptionName) {
		this.prOptionName = prOptionName;
	}

	public String getPrDescription() {
		return prDescription;
	}

	public void setPrDescription(String prDescription) {
		this.prDescription = prDescription;
	}

	@Override
	public String getId() {
		return this.getPrOptionId();
	}

	@Override
	public void setId(String id) {
		this.setPrOptionId(id);
		
	}

	@Override
	protected String getUniqueKey() {
		return this.getPrOptionName();
	}

}
