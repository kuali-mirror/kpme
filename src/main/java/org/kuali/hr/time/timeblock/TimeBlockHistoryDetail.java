package org.kuali.hr.time.timeblock;

import java.util.LinkedHashMap;

public class TimeBlockHistoryDetail extends TimeHourDetail{

	private static final long serialVersionUID = 1L;
	private String tkTimeBlockHistoryDetailId;
	private String tkTimeBlockHistoryId;
	
	@Override
	@SuppressWarnings("unchecked")
	protected LinkedHashMap toStringMapper() {
		return null;
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

}
