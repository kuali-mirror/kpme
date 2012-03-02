package org.kuali.hr.time.principal.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.dao.PrincipalHRAttributesDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class PrincipalHRAttributesServiceImpl implements PrincipalHRAttributesService {
	private PrincipalHRAttributesDao principalHRAttributesDao;

	public void setPrincipalHRAttributesDao(PrincipalHRAttributesDao principalHRAttributesDao) {
		this.principalHRAttributesDao = principalHRAttributesDao;
	}
	
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public PrincipalHRAttributes getPrincipalCalendar(String principalId, Date asOfDate){
		PrincipalHRAttributes pc =  this.principalHRAttributesDao.getPrincipalCalendar(principalId, asOfDate);
		if(pc != null) {
			pc.setCalendar(TkServiceLocator.getCalendarSerivce().getCalendarByGroup(pc.getPayCalendar()));
		}
		return pc;
	}
	
	/**
     * KPME-1250 Kagata
     * Get a list of active employees based on leave plan and as of a particular date
     */
    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public List<PrincipalHRAttributes> getActiveEmployeesForLeavePlan(String leavePlan, Date asOfDate) {
        List<PrincipalHRAttributes> principals = principalHRAttributesDao.getActiveEmployeesForLeavePlan(leavePlan, asOfDate);

        return principals;
    }
    
    @Override
	public PrincipalHRAttributes getPrincipalHRAttributes(String principalId) {
		return this.principalHRAttributesDao.getPrincipalHRAttributes(principalId);
	}

}
