package org.kuali.hr.time.assignment.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentAccount;
import org.kuali.kfs.coa.businessobject.Account;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
/**
 * Override the Maintenance page behavior for Assignment object
 * 
 *
 */
public class AssignmentMaintainableServiceImpl extends KualiMaintainableImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Preserves immutability of Assignments
	 */
	@Override
	public void saveBusinessObject() {
		Assignment assignment = (Assignment)this.getBusinessObject();
		assignment.setTimestamp(null);
		assignment.setTkAssignmentId(null);
		KNSServiceLocator.getBusinessObjectService().save(assignment);
		for(AssignmentAccount assignAcct : assignment.getAssignmentAccounts()){
			assignAcct.setTkAssignAcctId(null);
			assignAcct.setTkAssignmentId(assignment.getTkAssignmentId());
		}
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
			}else{
				fieldValues.put("name", "");
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall); 
	}
	
	@Override
	public Map<String, String> populateNewCollectionLines( Map<String, String> fieldValues, MaintenanceDocument maintenanceDocument, String methodToCall ) {
		if (fieldValues.containsKey("assignmentAccounts.accountNbr")
				&& StringUtils.isNotEmpty(fieldValues.get("assignmentAccounts.accountNbr"))) {
			Map<String, String> fields = new HashMap<String, String>();
			fields.put("accountNumber", fieldValues.get("assignmentAccounts.accountNbr"));
			Collection account = KNSServiceLocator.getBusinessObjectDao()
					.findMatching(Account.class, fields);
			if (account.size()>0) {
				Account acc = (Account) account.iterator().next();
				fieldValues.put("assignmentAccounts.finCoaCd", acc.getChartOfAccountsCode());
			}
		}
		return super.populateNewCollectionLines(fieldValues, maintenanceDocument, methodToCall);
	}
}
