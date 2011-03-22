package org.kuali.hr.job.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.accrual.AccrualCategory;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
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
		Job job = (Job) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long hrJobId = job.getHrJobId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&hrJobId=" + hrJobId
						+ "&principalId=&jobNumber=\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
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
			Collections.sort(objectList, new Comparator<BusinessObject>() {

				@Override
				public int compare(BusinessObject bo1, BusinessObject bo2) {
					int result = 0;
					if (bo1 instanceof AccrualCategory) {
						AccrualCategory acc1 = (AccrualCategory) bo1;
						AccrualCategory acc2 = (AccrualCategory) bo2;
						if (acc1.getTimestamp().before(acc2.getTimestamp())) {
							result = 1;
						}
					}
					return result;
				}
			});
			List<BusinessObject> objectListWithoutHistory = new ArrayList<BusinessObject>();
			objectListWithoutHistory.add(objectList.get(0));
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
