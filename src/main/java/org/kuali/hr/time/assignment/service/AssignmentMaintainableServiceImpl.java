package org.kuali.hr.time.assignment.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentAccount;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class AssignmentMaintainableServiceImpl extends KualiMaintainableImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		Assignment assignment = (Assignment)this.getBusinessObject();
//		Assignment oldAssignment = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Assignment.class, 
//									assignment.getTkAssignmentId());
//		if(oldAssignment != null){
//			oldAssignment.setActive(false);
//			KNSServiceLocator.getBusinessObjectService().save(oldAssignment);
//			for(AssignmentAccount oldAssignAccount : oldAssignment.getAssignmentAccounts()){
//				oldAssignAccount.setActive(false);
//				KNSServiceLocator.getBusinessObjectService().save(oldAssignAccount);
//			}
//		}
		assignment.setTimestamp(null);
		assignment.setTkAssignmentId(null);
		KNSServiceLocator.getBusinessObjectService().save(assignment);
		for(AssignmentAccount assignAcct : assignment.getAssignmentAccounts()){
			assignAcct.setTkAssignAcctId(null);
			assignAcct.setTkAssignmentId(assignment.getTkAssignmentId());
		}
	}

}
