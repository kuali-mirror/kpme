package org.kuali.hr.time.assignment.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentAccount;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.kfs.coa.businessobject.Account;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

/**
 * Override the Maintenance page behavior for Assignment object
 * 
 * 
 */
public class AssignmentMaintainableServiceImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Preserves immutability of Assignments
	 */
	@Override
	public void saveBusinessObject() {
		Assignment assignment = (Assignment) this.getBusinessObject();
		
		//Inactivate the old assignment as of the effective date of new assignment
		if(assignment.getTkAssignmentId()!=null && assignment.isActive()){
			Assignment oldAssignment = TkServiceLocator.getAssignmentService().getAssignment(assignment.getTkAssignmentId().toString());
			if(assignment.getEffectiveDate().equals(oldAssignment.getEffectiveDate())){
				assignment.setTimestamp(null);
			} else{
				if(oldAssignment!=null){
					oldAssignment.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldAssignment.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldAssignment.setEffectiveDate(assignment.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldAssignment);
				}
				assignment.setTimestamp(new Timestamp(System.currentTimeMillis()));
				assignment.setTkAssignmentId(null);
			
				for (AssignmentAccount assignAcct : assignment.getAssignmentAccounts()) {
					assignAcct.setTkAssignAcctId(null);
					assignAcct.setTkAssignmentId(assignment.getTkAssignmentId());
				}
			}
		}
		
		KNSServiceLocator.getBusinessObjectService().save(assignment);
	}

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

}
