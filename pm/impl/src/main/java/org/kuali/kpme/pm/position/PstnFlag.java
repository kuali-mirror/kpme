package org.kuali.kpme.pm.position;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;


public class PstnFlag extends PersistableBusinessObjectBase {
	private static final long serialVersionUID = 1L;
	
	private String pmFlagId;
	private String category;
	private String names;
	private String hrPositionId;
	
	public String getPmFlagId() {
		return pmFlagId;
	}
	public void setPmFlagId(String pmFlagId) {
		this.pmFlagId = pmFlagId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getHrPositionId() {
		return hrPositionId;
	}
	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}
	
}
