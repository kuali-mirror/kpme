package org.kuali.hr.lm.leavedonation.service;

import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.springframework.cache.annotation.Cacheable;

public interface LeaveDonationService {
    
    /**
     * Fetch LeaveDonation by id
     * @param lmLeaveDonationId
     * @return
     */
    @Cacheable(value= LeaveDonation.CACHE_NAME, key="'lmLeaveDonationId=' + #p0")
    public LeaveDonation getLeaveDonation(String lmLeaveDonationId);

}
