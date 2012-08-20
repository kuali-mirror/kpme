package org.kuali.hr.time.paytype.service;

import org.kuali.hr.time.paytype.PayType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.List;

public interface PayTypeService {
	/**
	 * Save or Update a Paytype
	 * @param payType
	 */
    @CacheEvict(value={PayType.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(PayType payType);
	/**
	 * Save or Update a List of PayType objects
	 * @param payTypeList
	 */
    @CacheEvict(value={PayType.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(List<PayType> payTypeList);
	
	/**
	 * Provides access to the PayType.   The PayCalendar will be loaded as well.
	 * @param payType
	 * @return A fully populated PayType.
	 */
    @Cacheable(value= PayType.CACHE_NAME, key="'payType=' + #p0 + '|' + 'effectiveDate=' + #p1")
	public PayType getPayType(String payType, Date effectiveDate);

    @Cacheable(value= PayType.CACHE_NAME, key="'hrPayTypeId=' + #p0")
	public PayType getPayType(String hrPayTypeId);
	
	/**
	 * get count of pay type with give payType
	 * @param payType
	 * @return int
	 */
	public int getPayTypeCount(String payType);
}
