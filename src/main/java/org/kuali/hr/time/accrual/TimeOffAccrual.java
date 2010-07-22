package org.kuali.hr.time.accrual;

import java.util.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TimeOffAccrual extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9086505395487714025L;

	private int id;
	
	private String principal;
	
	private String accrualCategory;
	
	private Date effectiveDate;
	
	private int hoursAccrued;
	
	private int hoursTaken;
	
	private int hoursAdjust;
	
	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", getId());
		map.put("principal", getPrincipal());
		map.put("accrualCategory", getAccrualCategory());
		map.put("effectiveDate", getEffectiveDate());
		map.put("hoursAccrued", getHoursAccrued());
		map.put("hoursTaken", getHoursTaken());
		map.put("hoursAdjust", getHoursAdjust());
		return map;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public int getHoursAccrued() {
		return hoursAccrued;
	}

	public void setHoursAccrued(int hoursAccrued) {
		this.hoursAccrued = hoursAccrued;
	}

	public int getHoursTaken() {
		return hoursTaken;
	}

	public void setHoursTaken(int hoursTaken) {
		this.hoursTaken = hoursTaken;
	}

	public int getHoursAdjust() {
		return hoursAdjust;
	}

	public void setHoursAdjust(int hoursAdjust) {
		this.hoursAdjust = hoursAdjust;
	}

}
