package org.kuali.hr.time.clock.location;

import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;

public class ClockLocationRuleIpAddress extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	private Long tkClockLocationRuleIpId;
	private Long tkClockLocationRuleId;
	private String ipAddress;
	
	@Override
	public Long getId() {
		return this.getTkClockLocationRuleIpId();
	}

	@Override
	public void setId(Long id) {
		this.setTkClockLocationRuleIpId(id);
	}
	@Override
	protected String getUniqueKey() {
		String ipAddressKey = getTkClockLocationRuleIpId().toString()
			+"_"+ getTkClockLocationRuleId().toString() + "_" + getIpAddress();
		return ipAddressKey;
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();

		toStringMap.put("tkClockLocationRuleIpId", tkClockLocationRuleIpId);
		toStringMap.put("tkOwnerObjectId", tkClockLocationRuleId);
		toStringMap.put("ipAddress", ipAddress);
		return null;
	}

	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getTkClockLocationRuleIpId() {
		return tkClockLocationRuleIpId;
	}

	public void setTkClockLocationRuleIpId(Long tkClockLocationRuleIpId) {
		this.tkClockLocationRuleIpId = tkClockLocationRuleIpId;
	}

	public Long getTkClockLocationRuleId() {
		return tkClockLocationRuleId;
	}

	public void setTkClockLocationRuleId(Long tkClockLocationRuleId) {
		this.tkClockLocationRuleId = tkClockLocationRuleId;
	}

}
