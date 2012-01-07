package org.kuali.hr.time.timeblock;

import java.util.LinkedHashMap;

import org.kuali.rice.kim.bo.Person;

public class TimeBlockHistoryDetail extends TimeHourDetail{

	private static final long serialVersionUID = 1L;
	private String tkTimeBlockHistoryDetailId;
	private String tkTimeBlockHistoryId;
	
	private TimeBlockHistory timeBlockHistory;
	
	private Person principal;
	private Person userPrincipal;
	
	@Override
	@SuppressWarnings({ "rawtypes" })
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String,String> lhm = new LinkedHashMap<String,String>();
		lhm.put(getEarnCode(), getEarnCode());
		return lhm;
	}

	public String getTkTimeBlockHistoryDetailId() {
		return tkTimeBlockHistoryDetailId;
	}

	public void setTkTimeBlockHistoryDetailId(String tkTimeBlockHistoryDetailId) {
		this.tkTimeBlockHistoryDetailId = tkTimeBlockHistoryDetailId;
	}

	public String getTkTimeBlockHistoryId() {
		return tkTimeBlockHistoryId;
	}

	public void setTkTimeBlockHistoryId(String tkTimeBlockHistoryId) {
		this.tkTimeBlockHistoryId = tkTimeBlockHistoryId;
	}

		
	public TimeBlockHistoryDetail(TimeHourDetail thd) {
		this.setEarnCode(thd.getEarnCode());
		this.setAmount(thd.getAmount());
		this.setHours(thd.getHours());
	}
	
    protected TimeBlockHistoryDetail(TimeBlockHistoryDetail t) {
        this.tkTimeBlockHistoryDetailId = t.tkTimeBlockHistoryDetailId;
        this.tkTimeBlockHistoryId = t.tkTimeBlockHistoryId;
        this.setEarnCode(t.getEarnCode());
        this.setHours(t.getHours());
        this.setAmount(t.getAmount());
    }

    public TimeBlockHistoryDetail copy() {
        return new TimeBlockHistoryDetail(this);
    }
    public TimeBlockHistoryDetail() {
    }

	public TimeBlockHistory getTimeBlockHistory() {
		return timeBlockHistory;
	}

	public void setTimeBlockHistory(TimeBlockHistory timeBlockHistory) {
		this.timeBlockHistory = timeBlockHistory;
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public Person getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(Person userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	public java.sql.Date getBeginDate() {
		return timeBlockHistory.getBeginDate();
	}



}
