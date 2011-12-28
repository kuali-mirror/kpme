package org.kuali.hr.time.salgroup;

import org.kuali.hr.time.HrBusinessObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class SalGroup extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hrSalGroupId;
	private String hrSalGroup;
	private String descr;
	private boolean history;

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String,String> lhm = new LinkedHashMap<String, String>();
		lhm.put(hrSalGroup, hrSalGroup);
		return lhm;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getHrSalGroupId() {
		return hrSalGroupId;
	}

	public void setHrSalGroupId(String hrSalGroupId) {
		this.hrSalGroupId = hrSalGroupId;
	}

	public String getHrSalGroup() {
		return hrSalGroup;
	}

	public void setHrSalGroup(String hrSalGroup) {
		this.hrSalGroup = hrSalGroup;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	protected String getUniqueKey() {
		return hrSalGroup;
	}

	@Override
	public String getId() {
		return getHrSalGroupId();
	}

	@Override
	public void setId(String id) {
		setHrSalGroupId(id);
	}

}
