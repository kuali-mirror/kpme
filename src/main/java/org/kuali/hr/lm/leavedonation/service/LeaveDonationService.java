package org.kuali.hr.lm.leavedonation.service;

import org.kuali.hr.lm.leavedonation.LeaveDonation;

public interface LeaveDonationService {
    
    /**
     * Fetch LeaveDonation by id
     * @param lmLeaveDonationId
     * @return
     */
    public LeaveDonation getLeaveDonation(String lmLeaveDonationId);

}
