package org.kuali.hr.pm.positionflag;

import org.kuali.hr.time.HrBusinessObject;

public class PositionFlag extends HrBusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	private String pmPositionFlagId;
	private String category;
	private String positionFlagName;
	
	public String getPmPositionFlagId() {
		return pmPositionFlagId;
	}
	public void setPmPositionFlagId(String pmPositionFlagId) {
		this.pmPositionFlagId = pmPositionFlagId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPositionFlagName() {
		return positionFlagName;
	}
	public void setPositionFlagName(String positionFlagName) {
		this.positionFlagName = positionFlagName;
	}
	
	@Override
	public String getId() {
		return this.getPmPositionFlagId();
	}
	@Override
	public void setId(String id) {
		this.setPmPositionFlagId(id);
		
	}
	@Override
	protected String getUniqueKey() {
		return this.getCategory() + "_" + this.getPositionFlagName();
	}
	
}
