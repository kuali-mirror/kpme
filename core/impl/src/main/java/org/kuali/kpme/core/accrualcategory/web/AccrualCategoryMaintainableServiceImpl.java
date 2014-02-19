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
package org.kuali.kpme.core.accrualcategory.web;

import org.kuali.kpme.core.accrualcategory.AccrualCategoryBo;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRuleBo;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * Override the Maintenance page behavior for Leave Accrual Category object
 * 
 * 
 */
public class AccrualCategoryMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Override
	protected void setNewCollectionLineDefaultValues(String arg0,
			PersistableBusinessObject arg1) {
    	if(arg1 instanceof AccrualCategoryRuleBo){
    		AccrualCategoryRuleBo leaveAccrualCategoryRule = (AccrualCategoryRuleBo)arg1;
    		AccrualCategoryBo leaveAccrualCategory = (AccrualCategoryBo) this.getBusinessObject();
    		leaveAccrualCategoryRule.setActive(leaveAccrualCategory.isActive());
    	}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		AccrualCategoryBo leaveAccrualCategory = (AccrualCategoryBo)hrObj;
		for (AccrualCategoryRuleBo accCatRule : leaveAccrualCategory.getAccrualCategoryRules()) {
			if(!isOldBusinessObjectInDocument()){ //prevents duplicate object on edit
				accCatRule.setLmAccrualCategoryId(null);
			}
			accCatRule.setLmAccrualCategoryId(leaveAccrualCategory.getLmAccrualCategoryId());
			accCatRule.setLmAccrualCategoryRuleId(null);
		}

        //CacheUtils.flushCache(AccrualCategory.CACHE_NAME);
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return AccrualCategoryBo.from(HrServiceLocator.getAccrualCategoryService().getAccrualCategory(id));
	}

    //KPME-2624 added logic to save current logged in user to UserPrincipal id for collections
    @Override
    public void prepareForSave() {
        AccrualCategoryBo accrualCategory = (AccrualCategoryBo)this.getBusinessObject();
        for (AccrualCategoryRuleBo accrualCategoryRule : accrualCategory.getAccrualCategoryRules()) {
            accrualCategoryRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        }
        super.prepareForSave();
    }
}
