package org.kuali.hr.time.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

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
		if (TKContext.getUser().getCurrentRoles().isSystemAdmin()) {
			Assignment assignment = (Assignment) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long tkAssignmentId = assignment.getTkAssignmentId();
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
							+ "&principalId=&jobNumber=\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}
	
	@SuppressWarnings({"unchecked" })
	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		
		String deptName = "";
		if(fieldValues.containsKey("dept") && !fieldValues.get("dept").isEmpty()) {
			deptName = fieldValues.get("dept");
			fieldValues.remove("dept");
		}
		List aList = super.getSearchResults(fieldValues);
		if(deptName.isEmpty()) {
			return aList;
		}
		List finalList = new ArrayList<Assignment>();
		for(int i = 0; i< aList.size(); i++) {
			Assignment anAssign = (Assignment) aList.get(i);
			if(anAssign.getPrincipalId() != null && anAssign.getEffectiveDate() != null) {
				List<Job> jobList = TkServiceLocator.getJobSerivce().getJobs(anAssign.getPrincipalId(), anAssign.getEffectiveDate());
				for(Job aJob : jobList) {
					if(!aJob.getJobNumber().equals(anAssign.getJobNumber())){
						continue;
					}				
					if( aJob.getDept().equals(deptName)) {
						finalList.add(anAssign);
						break;
					}
				}
			}
		}
		return finalList;
	}


}
