package org.kuali.hr.lm.accrual.service;

import org.kuali.hr.lm.accrual.LeaveAccrualCategory;
import org.kuali.hr.lm.accrual.LeaveAccrualCategoryRule;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.bo.PersistableBusinessObject;

/**
 * Override the Maintenance page behavior for Leave Accrual Category object
 * 
 * 
 */
public class LeaveAccrualMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Override
	protected void setNewCollectionLineDefaultValues(String arg0,
			PersistableBusinessObject arg1) {
    	if(arg1 instanceof LeaveAccrualCategoryRule){
    		LeaveAccrualCategoryRule leaveAccrualCategoryRule = (LeaveAccrualCategoryRule)arg1;
    		LeaveAccrualCategory leaveAccrualCategory = (LeaveAccrualCategory) this.getBusinessObject();
    		leaveAccrualCategoryRule.setActive(leaveAccrualCategory.isActive());
    	}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		LeaveAccrualCategory leaveAccrualCategory = (LeaveAccrualCategory)hrObj;
		for (LeaveAccrualCategoryRule accCatRule : leaveAccrualCategory.getAccrualCategoryRules()) {
			if(!isOldBusinessObjectInDocument()){ //prevents duplicate object on edit
				accCatRule.setLmAccrualCategoryId(null);
			}
			accCatRule.setLmAccrualCategoryId(leaveAccrualCategory.getLmAccrualCategoryId());
		}
	}

	@Override
	public HrBusinessObject getObjectById(Long id) {
		return TkServiceLocator.getLeaveAccrualCategoryService().getLeaveAccrualCategory(id);
	}

}
