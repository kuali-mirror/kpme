package org.kuali.hr.time.missedpunch;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizerBase;
import org.kuali.rice.kns.util.KNSConstants;

import uk.ltd.getahead.dwr.util.Logger;

public class MissedPunchAuthorizer extends TransactionalDocumentAuthorizerBase {

	private Logger LOG = Logger.getLogger(MissedPunchAuthorizer.class);
	
	@Override
	public boolean canEditDocumentOverview(Document document, Person user) {
		return true;
	}

	@Override
	public Set<String> getDocumentActions(Document document, Person user,
			Set<String> documentActions) {
		Set<String> author =  super.getDocumentActions(document, user, documentActions);
		MissedPunchDocument mpDoc = (MissedPunchDocument)document;
		if(StringUtils.equals(mpDoc.getDocumentStatus(),"R") ){
			try {
				if(KEWServiceLocator.getWorkflowUtilityService().isFinalApprover(Long.parseLong(mpDoc.getDocumentNumber()), TKContext.getPrincipalId())){
					author.add(KNSConstants.KUALI_ACTION_CAN_EDIT);
					author.add(KNSConstants.KUALI_ACTION_CAN_APPROVE);
					
					author.remove(KNSConstants.KUALI_ACTION_CAN_DISAPPROVE);
				}
			} catch (WorkflowException e) {
				LOG.error(e.toString());
			} 
		} else if(!StringUtils.equals(mpDoc.getDocumentStatus(), "A")){
			author.add(KNSConstants.KUALI_ACTION_CAN_EDIT);
			author.add(KNSConstants.KUALI_ACTION_CAN_ROUTE);
		}
		author.remove(KNSConstants.KUALI_ACTION_CAN_ADD_ADHOC_REQUESTS);
		author.remove(KNSConstants.KUALI_ACTION_CAN_SEND_ADHOC_REQUESTS);
		return author;
	}
	
	
	
}
