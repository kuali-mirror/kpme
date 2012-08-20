package org.kuali.hr.time.clock.location;

import org.kuali.hr.time.HrBusinessObject;

public class ClockLocationRuleIpAddress extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	private String tkClockLocationRuleIpId;
	private String tkClockLocationRuleId;
	private String ipAddress;
	
	@Override
	public String getId() {
		return this.getTkClockLocationRuleIpId();
	}

	@Override
	public void setId(String id) {
		this.setTkClockLocationRuleIpId(id);
	}
	@Override
	public String getUniqueKey() {
		String ipAddressKey = getTkClockLocationRuleIpId().toString()
			+"_"+ getTkClockLocationRuleId().toString() + "_" + getIpAddress();
		return ipAddressKey;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getTkClockLocationRuleIpId() {
		return tkClockLocationRuleIpId;
	}

	public void setTkClockLocationRuleIpId(String tkClockLocationRuleIpId) {
		this.tkClockLocationRuleIpId = tkClockLocationRuleIpId;
	}

	public String getTkClockLocationRuleId() {
		return tkClockLocationRuleId;
	}

	public void setTkClockLocationRuleId(String tkClockLocationRuleId) {
		this.tkClockLocationRuleId = tkClockLocationRuleId;
	}

}
