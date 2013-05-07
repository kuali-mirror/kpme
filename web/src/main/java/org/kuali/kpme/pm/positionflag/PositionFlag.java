package org.kuali.kpme.pm.positionflag;

import org.kuali.kpme.core.bo.HrBusinessObject;

import com.google.common.collect.ImmutableList;
public class PositionFlag extends HrBusinessObject {
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
												    .add("category")
												    .add("positionFlagName")
												    .build();
	
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
