package org.kuali.hr.time.earngroup.service;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.hr.time.earngroup.dao.EarnGroupDaoService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EarnGroupServiceImpl implements EarnGroupService {
    private EarnGroupDaoService earnGroupDao;

    @Override
    public EarnGroup getEarnGroup(String earnGroup, Date asOfDate) {
        return earnGroupDao.getEarnGroup(earnGroup, asOfDate);
    }

    public EarnGroupDaoService getEarnGroupDao() {
        return earnGroupDao;
    }

    public void setEarnGroupDao(EarnGroupDaoService earnGroupDao) {
        this.earnGroupDao = earnGroupDao;
    }

    @Override
    public EarnGroup getEarnGroupSummaryForEarnCode(String earnCode, Date asOfDate) {
        return earnGroupDao.getEarnGroupSummaryForEarnCode(earnCode, asOfDate);
    }

    @Override
    public EarnGroup getEarnGroupForEarnCode(String earnCode, Date asOfDate) {
        return earnGroupDao.getEarnGroupForEarnCode(earnCode, asOfDate);
    }

    public Set<String> getEarnCodeListForEarnGroup(String earnGroup, Date asOfDate) {
        Set<String> earnCodes = new HashSet<String>();
        EarnGroup earnGroupObj = earnGroupDao.getEarnGroup(earnGroup, asOfDate);
        if ( earnGroupObj != null ) {
            for (EarnGroupDefinition earnGroupDef : earnGroupObj.getEarnGroups()) {
                if (!earnCodes.contains(earnGroupDef.getEarnCode())) {
                    earnCodes.add(earnGroupDef.getEarnCode());
                }
            }
        }
        return earnCodes;
    }

    @Override
    public EarnGroup getEarnGroup(Long hrEarnGroupId) {
        return earnGroupDao.getEarnGroup(hrEarnGroupId);
    }
}
