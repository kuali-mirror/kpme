package org.kuali.hr.job.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.krad.bo.BusinessObject;

/**
 * Used to override default lookup behavior for the Job maintenance object
 */
public class JobLookupableHelper extends HrEffectiveDateActiveLookupableHelper {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
                                              @SuppressWarnings("rawtypes") List pkNames) {
        List<HtmlData> customActionUrls = super.getCustomActionUrls(
                businessObject, pkNames);
        if (TKContext.getUser().getCurrentRoles().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
            Job job = (Job) businessObject;
            final String className = this.getBusinessObjectClass().getName();
            final String hrJobId = job.getHrJobId();
            HtmlData htmlData = new HtmlData() {

                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

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
        String toEffdt = StringUtils.isNotBlank(fieldValues.get("effectiveDate")) ? fieldValues.get("effectiveDate").replace("<=", "") : "";
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(principalId, firstName, lastName, jobNumber,
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
