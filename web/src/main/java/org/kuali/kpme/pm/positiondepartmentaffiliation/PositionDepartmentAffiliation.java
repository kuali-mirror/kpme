package org.kuali.kpme.pm.positiondepartmentaffiliation;

import org.kuali.kpme.core.bo.HrBusinessObject;

import com.google.common.collect.ImmutableList;
public class PositionDepartmentAffiliation extends HrBusinessObject {
	
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
		    .add("positionDeptAfflType")
		    .build();
	
	private static final long serialVersionUID = 1L;
	
	private String pmPositionDeptAfflId;
	private String positionDeptAfflType;
	private boolean primaryIndicator;
	

	@Override
	public String getId() {
		return this.getPmPositionDeptAfflId();
	}

	@Override
	public void setId(String id) {
		setPmPositionDeptAfflId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionDeptAfflType();
	}

	/**
	 * @return the pmPositionDeptAfflId
	 */
	public String getPmPositionDeptAfflId() {
		return pmPositionDeptAfflId;
	}

	/**
	 * @param pmPositionDeptAfflId the pmPositionDeptAfflId to set
	 */
	public void setPmPositionDeptAfflId(String pmPositionDeptAfflId) {
		this.pmPositionDeptAfflId = pmPositionDeptAfflId;
	}

	/**
	 * @return the positionDeptAfflType
	 */
	public String getPositionDeptAfflType() {
		return positionDeptAfflType;
	}

	/**
	 * @param positionDeptAfflType the positionDeptAfflType to set
	 */
	public void setPositionDeptAfflType(String positionDeptAfflType) {
		this.positionDeptAfflType = positionDeptAfflType;
	}

	/**
	 * @return the primaryIndicator
	 */
	public boolean isPrimaryIndicator() {
		return primaryIndicator;
	}

	/**
	 * @param primaryIndicator the primaryIndicator to set
	 */
	public void setPrimaryIndicator(boolean primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}



}