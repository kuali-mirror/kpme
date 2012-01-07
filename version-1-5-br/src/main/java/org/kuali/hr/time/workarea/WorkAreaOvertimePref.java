package org.kuali.hr.time.workarea;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class WorkAreaOvertimePref extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long tkWorkAreaOvtPrefId;
	private Long tkWorkAreaId;
	private String payType;
	private String overtimePreference;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}


	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOvertimePreference() {
		return overtimePreference;
	}

	public void setOvertimePreference(String overtimePreference) {
		this.overtimePreference = overtimePreference;
	}


	public Long getTkWorkAreaOvtPrefId() {
		return tkWorkAreaOvtPrefId;
	}


	public void setTkWorkAreaOvtPrefId(Long tkWorkAreaOvtPrefId) {
		this.tkWorkAreaOvtPrefId = tkWorkAreaOvtPrefId;
	}

}
