package org.kuali.hr.job.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.web.struts.form.LookupForm;

/**
 * Used to override default lookup behavior for the Job maintenance object
 * 
 * 
 */
public class JobLookupableHelper extends KualiLookupableHelperServiceImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		if (TKContext.getUser().getCurrentRoles().isSystemAdmin()) {
			Job job = (Job) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long hrJobId = job.getHrJobId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&hrJobId="
							+ hrJobId + "&principalId=&jobNumber=\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}

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
		String showHistory = null;
		if (fieldValues.containsKey("history")) {
			showHistory = fieldValues.get("history");
			fieldValues.remove("history");
		}
		List<? extends BusinessObject> objectList = super
				.getSearchResults(fieldValues);
		if (!objectList.isEmpty()) {
			Map<String, String> fields = new HashMap<String, String>();
			fields.put("firstName", firstName);
			fields.put("lastName", lastName);
			List<Person> listPerson = KIMServiceLocator.getPersonService()
					.findPeople(fields);
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
		if (!objectList.isEmpty() && showHistory != null
				&& StringUtils.equals(showHistory, "N")) {
			Map<String, BusinessObject> objectsWithoutHistory = new HashMap<String, BusinessObject>();
			// Creating map for objects without history
			for (BusinessObject bo : objectList) {
				Job jobNew = (Job) bo;
				if (objectsWithoutHistory.containsKey(jobNew.getPrincipalId()
						+ jobNew.getJobNumber())) {
					// Comparing here for duplicates
					Job jobOld = (Job) objectsWithoutHistory.get(jobNew
							.getPrincipalId()
							+ jobNew.getJobNumber());
					int comparison = jobNew.getEffectiveDate().compareTo(
							jobOld.getEffectiveDate());
					// Comparison for highest effective date object to put
					switch (comparison) {
					case 0:
						if (jobNew.getTimestamp().after(jobOld.getTimestamp())) {
							// Sorting here by timestamp value
							objectsWithoutHistory.put(jobNew.getPrincipalId()
									+ jobNew.getJobNumber(), jobNew);
						}
						break;
					case 1:
						objectsWithoutHistory.put(jobNew.getPrincipalId()
								+ jobNew.getJobNumber(), jobNew);
					}
				} else {
					objectsWithoutHistory.put(jobNew.getPrincipalId()
							+ jobNew.getJobNumber(), jobNew);
				}
			}
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.addAll(objectsWithoutHistory.values());
			return objectListWithoutHistory;
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
