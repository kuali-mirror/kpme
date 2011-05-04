package org.kuali.hr.time.missedpunch.service;

import org.kuali.hr.time.missedpunch.MissedPunch;
import org.kuali.hr.time.missedpunch.dao.MissedPunchDao;

public class MissedPunchServiceImpl implements MissedPunchService {

    MissedPunchDao missedPunchDao;

    @Override
    public MissedPunch getMissedPunchByRouteHeader(Long headerId) {
        return missedPunchDao.getMissedPunchByRouteHeader(headerId);
    }

    public void setMissedPunchDao(MissedPunchDao missedPunchDao) {
        this.missedPunchDao = missedPunchDao;
    }
}
