/**
 * 
 */
package org.kuali.hr.time;

import java.util.Set;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentAuthorizerBase;

/**
 * @author Nikhil
 *
 */
public class BaseAuthorizer extends MaintenanceDocumentAuthorizerBase {

	@Override
	public Set<String> getDocumentActions(Document document, Person user,
			Set<String> documentActions) {
		if(!user.getPrincipalId().equals("admin") &&  documentActions.contains("canEdit")){
			documentActions.remove("canEdit");
		}
		return super.getDocumentActions(document, user, documentActions);
	}
	
}