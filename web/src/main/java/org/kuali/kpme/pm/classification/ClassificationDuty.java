package org.kuali.kpme.pm.classification;

import java.math.BigDecimal;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class ClassificationDuty extends PersistableBusinessObjectBase {
	private static final long serialVersionUID = 1L;
	
	private String pmDutyId;
	private String name;
	private String description;
	private BigDecimal percentage;
	private String pmPositionClassId;
	
	public String getPmDutyId() {
		return pmDutyId;
	}
	public void setPmDutyId(String pmDutyId) {
		this.pmDutyId = pmDutyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	public String getPmPositionClassId() {
		return pmPositionClassId;
	}
	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
	}
	
}
