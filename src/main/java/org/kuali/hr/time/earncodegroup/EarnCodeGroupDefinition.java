package org.kuali.hr.time.earncodegroup;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class EarnCodeGroupDefinition extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463674251885306591L;

	private String hrEarnCodeGroupDefId;

	private String earnCode;

	private String hrEarnCodeGroupId;

    private EarnCode earnCodeObj;

    // this is for the maintenance screen
    private String earnCodeDesc;

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}
	
	public String getHrEarnCodeGroupDefId() {
		return hrEarnCodeGroupDefId;
	}

	public void setHrEarnCodeGroupDefId(String hrEarnCodeGroupDefId) {
		this.hrEarnCodeGroupDefId = hrEarnCodeGroupDefId;
	}

	public String getHrEarnCodeGroupId() {
		return hrEarnCodeGroupId;
	}

	public void setHrEarnCodeGroupId(String hrEarnCodeGroupId) {
		this.hrEarnCodeGroupId = hrEarnCodeGroupId;
	}

	public void setEarnCodeDesc(String earnCodeDesc) {
		this.earnCodeDesc = earnCodeDesc;
	}

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}
	
	// this is for the maintenance screen
	public String getEarnCodeDesc() {
		EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(this.earnCode, new java.sql.Date(System.currentTimeMillis()));
		
		if(earnCode != null && StringUtils.isNotBlank(earnCode.getDescription())) {
			return earnCode.getDescription();
		}
		return "";
	}
}