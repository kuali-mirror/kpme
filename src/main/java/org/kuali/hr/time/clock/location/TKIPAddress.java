package org.kuali.hr.time.clock.location;

import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;

public class TKIPAddress extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	private Long tkIPAddressId;
	private Long tkClockLocationRuleId;
	private String ipAddress;
	
	@Override
	public Long getId() {
		return this.getTkIPAddressId();
	}

	@Override
	public void setId(Long id) {
		this.setTkIPAddressId(id);
	}
	@Override
	protected String getUniqueKey() {
		String ipAddressKey = getTkIPAddressId().toString()
			+"_"+ getTkClockLocationRuleId().toString() + "_" + getIpAddress();
		return ipAddressKey;
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();

		toStringMap.put("tkIPAddressId", tkIPAddressId);
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

	public Long getTkIPAddressId() {
		return tkIPAddressId;
	}

	public void setTkIPAddressId(Long tkIPAddressId) {
		this.tkIPAddressId = tkIPAddressId;
	}

	public Long getTkClockLocationRuleId() {
		return tkClockLocationRuleId;
	}

	public void setTkClockLocationRuleId(Long tkClockLocationRuleId) {
		this.tkClockLocationRuleId = tkClockLocationRuleId;
	}

}
