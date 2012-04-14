package org.kuali.hr.lm.accrual.service;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

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
    	if(arg1 instanceof AccrualCategoryRule){
    		AccrualCategoryRule leaveAccrualCategoryRule = (AccrualCategoryRule)arg1;
    		AccrualCategory leaveAccrualCategory = (AccrualCategory) this.getBusinessObject();
    		leaveAccrualCategoryRule.setActive(leaveAccrualCategory.isActive());
    	}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		AccrualCategory leaveAccrualCategory = (AccrualCategory)hrObj;
		for (AccrualCategoryRule accCatRule : leaveAccrualCategory.getAccrualCategoryRules()) {
			if(!isOldBusinessObjectInDocument()){ //prevents duplicate object on edit
				accCatRule.setLmAccrualCategoryId(null);
			}
			accCatRule.setLmAccrualCategoryId(leaveAccrualCategory.getLmAccrualCategoryId());
			accCatRule.setLmAccrualCategoryRuleId(null);
		}
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(id);
	}

}
