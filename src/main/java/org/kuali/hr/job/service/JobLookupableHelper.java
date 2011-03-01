package org.kuali.hr.job.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.hr.job.Job;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.web.struts.form.LookupForm;

public class JobLookupableHelper extends KualiLookupableHelperServiceImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		String firstName = null;
		String lastName = null;
		if (fieldValues.containsKey("firstName")) {
			firstName = fieldValues.get("firstName");
			fieldValues.remove("firstName");
		}
		if (fieldValues.containsKey("lastName")) {
			lastName = fieldValues.get("lastName");
			fieldValues.remove("lastName");
		}
		List<? extends BusinessObject> objectList = super
				.getSearchResults(fieldValues);
		if (!objectList.isEmpty()) {
			Map<String, String> fields = new HashMap<String, String>();
			fields.put("firstName", firstName);
			fields.put("lastName", lastName);
			List<Person> listPerson = KIMServiceLocator.getPersonService().findPeople(fields);
			List<String> listPrincipalId = new ArrayList<String>();
			for (Person person : listPerson) {
				listPrincipalId.add(person.getPrincipalId());
			}
			Iterator itr = objectList.iterator();
			while (itr.hasNext()) {
				Job job = (Job) itr.next();
				if (job.getPrincipalId() != null
						&& !listPrincipalId.contains(job.getPrincipalId())) {
					itr.remove();
				}
			}
		}
		return objectList;
	}

	@Override
	public HtmlData getReturnUrl(BusinessObject businessObject,
			LookupForm lookupForm, List returnKeys,
			BusinessObjectRestrictions businessObjectRestrictions) {
		if (lookupForm.getFieldConversions().containsKey("effectiveDate")) {
			lookupForm.getFieldConversions().remove("effectiveDate");
		}
		if (returnKeys.contains("effectiveDate")) {
			returnKeys.remove("effectiveDate");
		}
		return super.getReturnUrl(businessObject, lookupForm, returnKeys,
				businessObjectRestrictions);
	}
}
