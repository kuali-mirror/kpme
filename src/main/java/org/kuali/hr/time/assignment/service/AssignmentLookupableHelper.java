package org.kuali.hr.time.assignment.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class AssignmentLookupableHelper extends
        HrEffectiveDateActiveLookupableHelper {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
                                              List pkNames) {
        List<HtmlData> customActionUrls = super.getCustomActionUrls(
                businessObject, pkNames);
        if (TKContext.getUser().getCurrentRoles().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
            Assignment assignment = (Assignment) businessObject;
            final String className = this.getBusinessObjectClass().getName();
            final String tkAssignmentId = assignment.getTkAssignmentId();

            HtmlData htmlData = new HtmlData() {

                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                @Override
                public String constructCompleteHtmlTag() {

                    return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
                            + className
                            + "&methodToCall=start&tkAssignmentId="
                            + tkAssignmentId
                            + "\">view</a>";

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
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String fromEffdt = fieldValues.get("rangeLowerBoundKeyPrefix_effectiveDate");
        String toEffdt = StringUtils.isNotBlank(fieldValues.get("effectiveDate")) ? fieldValues.get("effectiveDate").replace("<=", "") : "";
        String principalId = fieldValues.get("principalId");
        String jobNumber = fieldValues.get("jobNumber");
        String dept = fieldValues.get("dept");
        String workArea = fieldValues.get("workArea");
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), principalId,
                jobNumber, dept, workArea, active, showHist);

//		String deptName = "";
//		if(fieldValues.containsKey("dept") && !fieldValues.get("dept").isEmpty()) {
//			deptName = fieldValues.get("dept");
//			fieldValues.remove("dept");
//		}
//		List aList = super.getSearchResults(fieldValues);
//		if(deptName.isEmpty()) {
//			return aList;
//		}
//		List finalList = new ArrayList<Assignment>();
//		for(int i = 0; i< aList.size(); i++) {
//			Assignment anAssign = (Assignment) aList.get(i);
//			if(anAssign.getPrincipalId() != null && anAssign.getEffectiveDate() != null) {
//				List<Job> jobList = TkServiceLocator.getJobSerivce().getJobs(anAssign.getPrincipalId(), anAssign.getEffectiveDate());
//				for(Job aJob : jobList) {
//					if(!aJob.getJobNumber().equals(anAssign.getJobNumber())){
//						continue;
//					}
//					if( aJob.getDept().equals(deptName)) {
//						finalList.add(anAssign);
//						break;
//					}
//				}
//			}
//		}
        return assignments;
    }


}
