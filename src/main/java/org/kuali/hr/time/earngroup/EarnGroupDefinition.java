package org.kuali.hr.time.earngroup;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EarnGroupDefinition extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463674251885306591L;

	private Long tkEarnGroupDefId;

	private String earnCode;

	private Long tkEarnGroupId;

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}
	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getTkEarnGroupDefId() {
		return tkEarnGroupDefId;
	}

	public void setTkEarnGroupDefId(Long tkEarnGroupDefId) {
		this.tkEarnGroupDefId = tkEarnGroupDefId;
	}

	public Long getTkEarnGroupId() {
		return tkEarnGroupId;
	}

	public void setTkEarnGroupId(Long tkEarnGroupId) {
		this.tkEarnGroupId = tkEarnGroupId;
	}

}
