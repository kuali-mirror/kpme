package org.kuali.hr.time.assignment.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentAccount;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.kfs.coa.businessobject.Account;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;

/**
 * Override the Maintenance page behavior for Assignment object
 * 
 * 
 */
public class AssignmentMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("principalId")
				&& StringUtils.isNotEmpty(fieldValues.get("principalId"))) {
			Person p = KIMServiceLocator.getPersonService().getPerson(
					fieldValues.get("principalId"));
			if (p != null) {
				fieldValues.put("name", p.getName());
			} else {
				fieldValues.put("name", "");
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

	@Override
	public Map<String, String> populateNewCollectionLines(
			Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("assignmentAccounts.accountNbr")
				&& StringUtils.isNotEmpty(fieldValues
						.get("assignmentAccounts.accountNbr"))) {
			Map<String, String> fields = new HashMap<String, String>();
			fields.put("accountNumber", fieldValues
					.get("assignmentAccounts.accountNbr"));
			Collection account = KNSServiceLocator.getBusinessObjectDao()
					.findMatching(Account.class, fields);
			if (account.size() > 0) {
				Account acc = (Account) account.iterator().next();
				fieldValues.put("assignmentAccounts.finCoaCd", acc
						.getChartOfAccountsCode());
			}
		}
		return super.populateNewCollectionLines(fieldValues,
				maintenanceDocument, methodToCall);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		Assignment aOld = (Assignment) document.getOldMaintainableObject()
				.getBusinessObject();
		Assignment aNew = (Assignment) document.getNewMaintainableObject()
				.getBusinessObject();
		for (AssignmentAccount aAccount : aOld.getAssignmentAccounts()) {
			aAccount.setActive(aOld.isActive());
		}
		for (AssignmentAccount aAccount : aNew.getAssignmentAccounts()) {
			aAccount.setActive(aOld.isActive());
		}
		super.processAfterEdit(document, parameters);
	}


    @Override
	protected void setNewCollectionLineDefaultValues(String arg0,
			PersistableBusinessObject arg1) {
    	if(arg1 instanceof AssignmentAccount){
    		AssignmentAccount assignmentAccount = (AssignmentAccount)arg1;
    		Assignment assignment = (Assignment) this.getBusinessObject();
    		assignmentAccount.setActive(assignment.isActive());
    	}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

	@Override
	public HrBusinessObject getObjectById(Long id) {
		return TkServiceLocator.getAssignmentService().getAssignment(id);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		Assignment assignment = (Assignment)hrObj;
		for (AssignmentAccount assignAcct : assignment.getAssignmentAccounts()) {
			assignAcct.setTkAssignAcctId(null);
			assignAcct.setTkAssignmentId(assignment.getTkAssignmentId());
		}
	}

}
