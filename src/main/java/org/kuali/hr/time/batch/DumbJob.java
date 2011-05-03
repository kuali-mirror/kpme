package org.kuali.hr.time.batch;

public class DumbJob extends BatchJob{

	@Override
	public void doWork() {
		System.err.println("testing dumb job");
	}

	@Override
	protected void populateBatchJobEntry(Object o) {
		// TODO Auto-generated method stub

	}

}
