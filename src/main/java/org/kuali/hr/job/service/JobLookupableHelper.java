/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.job.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class JobLookupableHelper extends HrEffectiveDateActiveLookupableHelper {

	private static final long serialVersionUID = 3233495722838070429L;

	@Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
    	List<HtmlData> customActionUrls = new ArrayList<HtmlData>();
		
		List<HtmlData> defaultCustomActionUrls = super.getCustomActionUrls(businessObject, pkNames);
        
		Job job = (Job) businessObject;
        String hrJobId = job.getHrJobId();
        String jobNumber = String.valueOf(job.getJobNumber());
        String principalId = job.getPrincipalId();
        String location = job.getLocation();
        String department = job.getDept();
        
        boolean systemAdmin = TKContext.getUser().isSystemAdmin();
		boolean locationAdmin = TKContext.getUser().getLocationAdminAreas().contains(location);
		boolean departmentAdmin = TKContext.getUser().getDepartmentAdminAreas().contains(department);
		
		for (HtmlData defaultCustomActionUrl : defaultCustomActionUrls){
			if (StringUtils.equals(defaultCustomActionUrl.getMethodToCall(), "edit")) {
				if (systemAdmin || locationAdmin || departmentAdmin) {
					customActionUrls.add(defaultCustomActionUrl);
				}
			} else {
				customActionUrls.add(defaultCustomActionUrl);
			}
		}
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("hrJobId", hrJobId);
		params.put("jobNumber", jobNumber);
		params.put("principalId", principalId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<? extends BusinessObject> getSearchResults(
            Map<String, String> fieldValues) {

        String principalId = fieldValues.get("principalId");
        String firstName = fieldValues.get("firstName");
        String lastName = fieldValues.get("lastName");
        String jobNumber = fieldValues.get("jobNumber");
        String dept = fieldValues.get("dept");
        String positionNumber = fieldValues.get("positionNumber");
        String hrPayType = fieldValues.get("hrPayType");
        String fromEffdt = fieldValues.get("rangeLowerBoundKeyPrefix_effectiveDate");
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        List<Job> jobs = TkServiceLocator.getJobService().getJobs(principalId, firstName, lastName, jobNumber,
                dept, positionNumber, hrPayType,
                TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt),
                active,  showHist);

        return jobs;


//		String firstName = null;
//		String lastName = null;
//		if (fieldValues.containsKey("firstName")) {
//			firstName = fieldValues.get("firstName");
//			fieldValues.remove("firstName");
//		}
//		if (fieldValues.containsKey("lastName")) {
//			lastName = fieldValues.get("lastName");
//			fieldValues.remove("lastName");
//		}


//		if(StringUtils.isNotEmpty(firstName) || StringUtils.isNotEmpty(lastName)){
//			Map<String, String> fields = new HashMap<String, String>();
//			fields.put("firstName", firstName);
//			fields.put("lastName", lastName);
//			List<Person> listPerson = KIMServiceLocator.getPersonService()
//					.findPeople(fields);
//			List<Job> objectList = new ArrayList<Job>();
//			for(Person p: listPerson){
//				fieldValues.put("principalId", p.getPrincipalId());
//				objectList.addAll((Collection<? extends Job>) super.getSearchResults(fieldValues));
//			}
//			//trim if greater than 300 items
//			if(objectList.size() > 300){
//				objectList = objectList.subList(0, 299);
//			}
//			return objectList;
//		}

//        return super.getSearchResults(fieldValues);
    }

    @SuppressWarnings("rawtypes")
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
