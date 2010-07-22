package org.kuali.hr.time.pay;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class PayType extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1060360351158357134L;

	private int id;
	
	private String type;
	
	private String description;
	
	private String calendarGroup;
	
	private String regularEarnCode;
	
	private Date effectiveDate;
	
	private Timestamp timestamp;
	
	private String holidayCalendarGroup;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCalendarGroup() {
		return calendarGroup;
	}

	public void setCalendarGroup(String calendarGroup) {
		this.calendarGroup = calendarGroup;
	}

	public String getRegularEarnCode() {
		return regularEarnCode;
	}

	public void setRegularEarnCode(String regularEarnCode) {
		this.regularEarnCode = regularEarnCode;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getHolidayCalendarGroup() {
		return holidayCalendarGroup;
	}

	public void setHolidayCalendarGroup(String holidayCalendarGroup) {
		this.holidayCalendarGroup = holidayCalendarGroup;
	}

	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", getId());
		map.put("type", getType());
		map.put("description", getDescription());
		map.put("calendarGroup", getCalendarGroup());
		map.put("regularEarnCode", getRegularEarnCode());
		map.put("effectiveDate", getEffectiveDate());
		map.put("timestamp", getTimestamp());
		map.put("holidayCalendarGroup", getHolidayCalendarGroup());
		return map;
	}

}
