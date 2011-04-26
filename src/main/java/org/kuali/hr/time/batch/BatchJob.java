package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.config.ConfigContext;

public abstract class BatchJob {
	int lastPlace = 0;
	
	private Long tkBatchJobId;
	private String batchJobName;
	private String batchJobStatus;
	private Long payCalendarEntryId;
	private Long timeElapsed;
	
	public abstract void runJob();
	
	public String getNextIpAddressInCluster(){
		String clusterIps = ConfigContext.getCurrentContextConfig().getProperty("cluster.ips");
		String[] ips = StringUtils.split(clusterIps,",");
		if(ips != null){
			String ip = ips[lastPlace++];
			if(lastPlace >=ip.length()){
				lastPlace = 0;
			}
			return ip;
		}
		return "";
	}
	
	protected abstract void populateBatchJobEntry();

	public Long getTkBatchJobId() {
		return tkBatchJobId;
	}

	public void setTkBatchJobId(Long tkBatchJobId) {
		this.tkBatchJobId = tkBatchJobId;
	}

	public String getBatchJobName() {
		return batchJobName;
	}

	public void setBatchJobName(String batchJobName) {
		this.batchJobName = batchJobName;
	}

	public String getBatchJobStatus() {
		return batchJobStatus;
	}

	public void setBatchJobStatus(String batchJobStatus) {
		this.batchJobStatus = batchJobStatus;
	}

	public Long getPayCalendarEntryId() {
		return payCalendarEntryId;
	}

	public void setPayCalendarEntryId(Long payCalendarEntryId) {
		this.payCalendarEntryId = payCalendarEntryId;
	}

	public Long getTimeElapsed() {
		return timeElapsed;
	}

	public void setTimeElapsed(Long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}
}
