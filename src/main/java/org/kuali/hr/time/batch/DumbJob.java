package org.kuali.hr.time.batch;

public class DumbJob extends BatchJob{

	public DumbJob(){
		super();
		this.setBatchJobName("DumbJob");
		this.setHrPyCalendarEntryId("1");
	}
	
	@Override
	public void doWork() {
		System.err.println("testing dumb job");
	}

	@Override
	protected void populateBatchJobEntry(Object o) {
		// TODO Auto-generated method stub

	}

}
