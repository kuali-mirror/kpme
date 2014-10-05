package org.kuali.kpme.edo.submitButton;

import java.math.BigDecimal;
import java.sql.Date;

public class EdoSubmitButton {
	
	 private BigDecimal edoSubmitButtonDisplayId;
	 private String campusCode;
	 private boolean activeFlag;
	
	 
	public BigDecimal getEdoSubmitButtonDisplayId() {
		return edoSubmitButtonDisplayId;
	}
	public void setEdoSubmitButtonDisplayId(BigDecimal edoSubmitButtonDisplayId) {
		this.edoSubmitButtonDisplayId = edoSubmitButtonDisplayId;
	}
	public String getCampusCode() {
		return campusCode;
	}
	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}
	public boolean isActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	
	 
	 

}
