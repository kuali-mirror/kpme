package org.kuali.kpme.pm.position;

import java.math.BigDecimal;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class PositionDuty extends PersistableBusinessObjectBase {
private static final long serialVersionUID = 1L;
	
	private String pmDutyId;
	private String name;
	private String description;
	private BigDecimal percentage;
	private String hrPositionId;
	
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
	public String getHrPositionId() {
		return hrPositionId;
	}
	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}
	
	
}
