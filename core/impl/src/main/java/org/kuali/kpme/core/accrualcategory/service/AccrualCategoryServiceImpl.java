/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.accrualcategory.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategoryBo;
import org.kuali.kpme.core.accrualcategory.dao.AccrualCategoryDao;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryService;
import org.kuali.kpme.core.api.accrualcategory.AccrualEarnInterval;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

import java.util.List;

public class AccrualCategoryServiceImpl implements AccrualCategoryService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AccrualCategoryServiceImpl.class);
	private AccrualCategoryDao accrualCategoryDao;
    private static final ModelObjectUtils.Transformer<AccrualCategoryBo, AccrualCategory> toAccrualCategory =
            new ModelObjectUtils.Transformer<AccrualCategoryBo, AccrualCategory>() {
                public AccrualCategory transform(AccrualCategoryBo input) {
                    return AccrualCategoryBo.to(input);
                };
            };

	public AccrualCategory getAccrualCategory(String accrualCategory, LocalDate asOfDate) {
		return AccrualCategoryBo.to(accrualCategoryDao.getAccrualCategory(accrualCategory, asOfDate));
	}

	@Override
	public AccrualCategory saveOrUpdate(AccrualCategory accrualCategory) {
        if (accrualCategory == null) {
            return null;
        }
        return AccrualCategoryBo.to(KRADServiceLocatorWeb.getLegacyDataAdapter().save(AccrualCategoryBo.from(accrualCategory)));
	}

	public AccrualCategoryDao getAccrualCategoryDao() {
		return accrualCategoryDao;
	}

	public void setAccrualCategoryDao(AccrualCategoryDao accrualCategoryDao) {
		this.accrualCategoryDao = accrualCategoryDao;
	}

	@Override
	public AccrualCategory getAccrualCategory(String lmAccrualCategoryId) {
		return AccrualCategoryBo.to(accrualCategoryDao.getAccrualCategory(lmAccrualCategoryId));
	}

	@Override
	public List <AccrualCategory> getActiveAccrualCategories(LocalDate asOfDate){
		return ModelObjectUtils.transform(accrualCategoryDao.getActiveAccrualCategories(asOfDate), toAccrualCategory);
	}

    @Override
    public List<AccrualCategory> getAccrualCategories(String accrualCategory, String accrualCatDescr, String leavePlan, String accrualEarnInterval, String unitOfTime, String minPercentWorked, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        return ModelObjectUtils.transform(accrualCategoryDao.getAccrualCategories(accrualCategory, accrualCatDescr, leavePlan, accrualEarnInterval, unitOfTime, minPercentWorked, fromEffdt, toEffdt, active, showHistory), toAccrualCategory);
    }
   

	public void runAccrual(String principalId, LocalDate asOfDate){
		PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
		if(principalHRAttributes == null){
			LOG.error("Cannot find principal hr attributes for "+principalId);
			return;
		}
		//Grab the service date
		LocalDate serviceDate = principalHRAttributes.getServiceLocalDate();
		if(serviceDate == null){
			LOG.error("Cannot find service date on principal hr attribute for "+principalId);
			return;
		}
		
		String leavePlanStr = principalHRAttributes.getLeavePlan();
		if(StringUtils.isBlank(leavePlanStr)){
			LOG.error("Cannot find leave plan for "+principalId);
			return;
		}
		LeavePlanContract leavePlan = HrServiceLocator.getLeavePlanService().getLeavePlan(leavePlanStr, asOfDate);
		if(leavePlan == null){
			LOG.error("Cannot find leave plan object for leave plan " + leavePlanStr);
			return;
		}

		//Grab all the accrual categories for leave plan
		List<AccrualCategoryBo> accrualCategories = accrualCategoryDao.getActiveAccrualCategories(leavePlanStr, asOfDate);
		for(AccrualCategoryBo accrualCat : accrualCategories){
			//if no rules continue
			if(StringUtils.equals(accrualCat.getAccrualEarnInterval(), AccrualEarnInterval.NO_ACCRUAL.getCode())
					|| accrualCat.getAccrualCategoryRules().isEmpty()){
				continue;
			}
			String serviceUnitOfTime = accrualCat.getUnitOfTime();
			accrualCat.getAccrualEarnInterval();
			
		}
	}	

	public List<AccrualCategory> getActiveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate) {
    	return ModelObjectUtils.transform(accrualCategoryDao.getActiveAccrualCategories(leavePlan, asOfDate), toAccrualCategory);
    }
    
	 @Override
    public List<AccrualCategory> getActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate) {
    	return ModelObjectUtils.transform(accrualCategoryDao.getActiveLeaveAccrualCategoriesForLeavePlan(leavePlan, asOfDate), toAccrualCategory);
    }
    
    @Override
    public List<AccrualCategory> getInActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate) {
    	return ModelObjectUtils.transform(accrualCategoryDao.getInActiveLeaveAccrualCategoriesForLeavePlan(leavePlan, asOfDate), toAccrualCategory);
    }

}
