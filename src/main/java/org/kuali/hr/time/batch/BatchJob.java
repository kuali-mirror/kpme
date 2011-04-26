package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.config.ConfigContext;

public abstract class BatchJob {
	int lastPlace = 0;
	
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
}
