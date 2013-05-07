package org.kuali.kpme.pm.classification;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class ClassificationFlag extends PersistableBusinessObjectBase {
	private static final long serialVersionUID = 1L;
	
	private String pmFlagId;
	private String category;
	private String name;
	private String pmPositionClassId;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPmPositionClassId() {
		return pmPositionClassId;
	}
	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
	}
}
