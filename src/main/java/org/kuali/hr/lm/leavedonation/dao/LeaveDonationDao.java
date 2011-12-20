package org.kuali.hr.lm.leavedonation.dao;

import org.kuali.hr.lm.leavedonation.LeaveDonation;

public interface LeaveDonationDao {

		/**
	 * Get leave Donationfrom id
	 * @param lmLeaveDonationId
	 * @return LeaveDonation
	 */
	public LeaveDonation getLeaveDonation(Long lmLeaveDonationId);
	
}
