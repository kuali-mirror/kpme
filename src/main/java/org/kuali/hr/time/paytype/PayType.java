package org.kuali.hr.time.paytype;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class PayType extends PersistableBusinessObjectBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long payTypeId;
	private String payType;
	private String descr;
	private String calendarGroup;
	private String regEarnCode;
	private Date effectiveDate;
	private Timestamp timestamp;
	private String holidayCalendarGroup;

	private HolidayCalendar holidayCalendar;
	private PayCalendar payCalendar;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}

	public Long getPayTypeId() {
		return payTypeId;
	}

	public void setPayTypeId(Long payTypeId) {
		this.payTypeId = payTypeId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCalendarGroup() {
		return calendarGroup;
	}

	public void setCalendarGroup(String calendarGroup) {
		this.calendarGroup = calendarGroup;
	}

	public String getRegEarnCode() {
		return regEarnCode;
	}

	public void setRegEarnCode(String regEarnCode) {
		this.regEarnCode = regEarnCode;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setHolidayCalendarGroup(String holidayCalendarGroup) {
		this.holidayCalendarGroup = holidayCalendarGroup;
	}

	public String getHolidayCalendarGroup() {
		return holidayCalendarGroup;
	}

	public HolidayCalendar getHolidayCalendar() {
		return holidayCalendar;
	}

	public void setHolidayCalendar(HolidayCalendar holidayCalendar) {
		this.holidayCalendar = holidayCalendar;
	}

	public PayCalendar getPayCalendar() {
		return payCalendar;
	}

	public void setPayCalendar(PayCalendar payCalendar) {
		this.payCalendar = payCalendar;
	}
}
