package org.kuali.hr.time.batch;

import uk.ltd.getahead.dwr.util.Logger;

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
