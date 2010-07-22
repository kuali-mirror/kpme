package org.kuali.hr.time.holiday;

import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class HolidayCalendar extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3012370506000823320L;

	private int id;

	private String group;

	private String description;

	private List<HolidayCalendarDate> holidays;

	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", getId());
		map.put(group, getGroup());
		map.put("description", getDescription());
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		if (holidays != null)
			for (HolidayCalendarDate holiday : holidays)
				buf.append(holiday.toStringMapper().toString());
		buf.append("]");
		map.put("holidays", buf.toString());
		return map;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGroup() {
		return group;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setHolidays(List<HolidayCalendarDate> holidays) {
		this.holidays = holidays;
	}

	public List<HolidayCalendarDate> getHolidays() {
		return holidays;
	}

}
