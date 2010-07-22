package org.kuali.hr.time.sal;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class SalGroup extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6783836468814429294L;

	private int id;

	private String group;

	private Date effectiveDate;

	private Timestamp timestamp;

	private boolean active;

	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", getId());
		map.put("group", getGroup());
		map.put("effectiveDate", getEffectiveDate());
		map.put("timestamp", getTimestamp());
		map.put("active", isActive());
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

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

}
