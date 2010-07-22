package org.kuali.hr.time.holiday;

import java.util.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class HolidayCalendarDate extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -173022931491444880L;

	private int id;

	private Date holiday;

	private String description;

	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", getId());
		map.put("holiday", getHoliday());
		map.put("description", getDescription());
		return map;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setHoliday(Date holiday) {
		this.holiday = holiday;
	}

	public Date getHoliday() {
		return holiday;
	}

}
