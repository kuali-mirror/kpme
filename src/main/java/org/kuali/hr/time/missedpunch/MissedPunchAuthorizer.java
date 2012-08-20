package org.kuali.hr.time.missedpunch;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizerBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.KRADConstants;

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
			if(KewApiServiceLocator.getWorkflowDocumentActionsService().isFinalApprover(mpDoc.getDocumentNumber(), TKContext.getPrincipalId())){
				author.add(KRADConstants.KUALI_ACTION_CAN_EDIT);
				author.add(KRADConstants.KUALI_ACTION_CAN_APPROVE);
				
				author.remove(KRADConstants.KUALI_ACTION_CAN_DISAPPROVE);
			}
		} else if(!StringUtils.equals(mpDoc.getDocumentStatus(), "A")){
			author.add(KRADConstants.KUALI_ACTION_CAN_EDIT);
			author.add(KRADConstants.KUALI_ACTION_CAN_ROUTE);
		}
		author.remove(KRADConstants.KUALI_ACTION_CAN_ADD_ADHOC_REQUESTS);
		author.remove(KRADConstants.KUALI_ACTION_CAN_SEND_ADHOC_REQUESTS);
		return author;
	}
	
	
	
}
