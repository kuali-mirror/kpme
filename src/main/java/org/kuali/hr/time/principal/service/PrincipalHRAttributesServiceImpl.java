package org.kuali.hr.time.principal.service;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.dao.PrincipalHRAttributesDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

import java.util.Date;

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

}
