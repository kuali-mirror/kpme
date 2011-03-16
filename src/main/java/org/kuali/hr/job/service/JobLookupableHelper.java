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
import org.kuali.rice.kns.web.struts.form.LookupForm;

public class JobLookupableHelper extends KualiLookupableHelperServiceImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String addToReturnHref(String href, LookupForm lookupForm) {
		System.out.println("In method addToReturnHref");
		return super.addToReturnHref("href", lookupForm);
	}

	@Override
	public boolean allowsNewOrCopyAction(String documentTypeName) {
		System.out.println("In method allowsNewOrCopyAction");
		return super.allowsNewOrCopyAction(documentTypeName);
	}

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		System.out.println("In method getCustomActionUrls");
		if(pkNames!=null){
			for(Object obj:pkNames){
				System.out.println("Pk names are "+obj);
			}
		}
		return super.getCustomActionUrls(businessObject, pkNames);
	}

	@Override
	public boolean performCustomAction(boolean ignoreErrors) {
		System.out.println("In method performCustomAction");
		return super.performCustomAction(ignoreErrors);
	}

	@Override
	protected String getActionUrlHref(BusinessObject businessObject,
			String methodToCall, List pkNames) {
		System.out.println("In method getActionUrlHref");
		System.out.println("Method to call is : "+methodToCall);
		if(pkNames!=null){
			for(Object obj:pkNames){
				System.out.println("Pk names are "+obj);
			}
		}
		return super.getActionUrlHref(businessObject, methodToCall, pkNames);
	}

	@Override
	protected String getActionUrlTitleText(BusinessObject businessObject,
			String displayText, List pkNames,
			BusinessObjectRestrictions businessObjectRestrictions) {
		System.out.println("In method getActionUrlTitleText : "+displayText);
		if(pkNames!=null){
			for(Object obj:pkNames){
				System.out.println("Pk names are "+obj);
			}
		}
		return super.getActionUrlTitleText(businessObject, displayText, pkNames,
				businessObjectRestrictions);
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
		if (!objectList.isEmpty() && showHistory != null
				&& StringUtils.equals(showHistory, "N")) {
			Collections.sort(objectList, new Comparator<BusinessObject>() {

				@Override
				public int compare(BusinessObject bo1, BusinessObject bo2) {
					int result = 0;
					if(bo1 instanceof AccrualCategory){
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
		System.out.println("In method Get Return URL ");
		for(Object obj:lookupForm.getActionFormUtilMap().keySet()){
			System.out.println(obj);
		}
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
