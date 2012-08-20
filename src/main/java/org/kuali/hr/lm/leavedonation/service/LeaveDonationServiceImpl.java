package org.kuali.hr.lm.leavedonation.service;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.hr.lm.leavedonation.dao.LeaveDonationDao;

public class LeaveDonationServiceImpl implements LeaveDonationService {

    private static final Logger LOG = Logger.getLogger(LeaveDonationServiceImpl.class);
    private LeaveDonationDao leaveDonationDao;


    public LeaveDonationDao getLeaveDonationDao() {
        return leaveDonationDao;
    }


    public void setLeaveDonationDao(LeaveDonationDao leaveDonationDao) {
        this.leaveDonationDao = leaveDonationDao;
    }


    @Override
    public LeaveDonation getLeaveDonation(String lmLeaveDonationId) {
        return getLeaveDonationDao().getLeaveDonation(lmLeaveDonationId);
    }

}
