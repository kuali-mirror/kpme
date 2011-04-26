package org.kuali.hr.time.batch;

import org.apache.log4j.Logger;


public class InitiateBatchJob extends BatchJob{
	private Logger LOG = Logger.getLogger(InitiateBatchJob.class);
	
	
	@Override
	public void runJob() {
		LOG.info("Starting initiate batch job");
		
		
		
		
		LOG.info("Finished initiate batch job");
	}


	@Override
	protected void populateBatchJobEntry() {
		// TODO Auto-generated method stub
		
	}

}
