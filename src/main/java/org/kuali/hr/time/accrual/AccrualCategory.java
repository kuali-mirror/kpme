package org.kuali.hr.time.accrual;

import java.util.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class AccrualCategory extends PersistableBusinessObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2199784209037101946L;

	private int id;
	
	private String category;
	
	private String description;
	
	private Date effectiveDate;
	
	private boolean active;
	
	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", getId());
		map.put("category", getCategory());
		map.put("description", getDescription());
		map.put("effectiveDate", getEffectiveDate());
		map.put("active", isActive());
		return map;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
