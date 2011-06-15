package org.kuali.hr.time.assignment.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class AssignmentLookupableHelper extends
		KualiLookupableHelperServiceImpl {
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
			Assignment assignment = (Assignment) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long tkAssignmentId = assignment.getTkAssignmentId();
			HtmlData htmlData = new HtmlData() {

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

	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		String showHistory = "Y";
		if (fieldValues.containsKey("history")) {
			showHistory = fieldValues.get("history");
			fieldValues.remove("history");
		}
		String active = "";
		if(fieldValues.containsKey("active")){
			active = fieldValues.get("active");
			fieldValues.put("active", "");
		}

		List<? extends BusinessObject> objectList = super.getSearchResults(fieldValues);
		List<BusinessObject> finalBusinessObjectList = new ArrayList<BusinessObject>();

		//Create a principalId+jobNumber map as this is the unique key for results
		Map<String,List<Assignment>> jobToAssignmentMap = new HashMap<String,List<Assignment>>();

		for(BusinessObject bo : objectList){
			Assignment assign = (Assignment)bo;
			String jobKey = assign.getPrincipalId()+"_"+assign.getJobNumber()+"_"+assign.getWorkArea()+"_"+
								assign.getTask() != null ? assign.getTask().toString() : "";
			if(jobToAssignmentMap.get(jobKey)!=null){
				List<Assignment> lstAssignments = jobToAssignmentMap.get(jobKey);
				lstAssignments.add(assign);
			} else {
				List<Assignment> lstAssignments = new ArrayList<Assignment>();
				lstAssignments.add(assign);
				jobToAssignmentMap.put(jobKey, lstAssignments);
			}
		}

		//Sort by EffectiveDate/Timestamp and reverse each list so it goes from future back
		for(List<Assignment> lstAssign : jobToAssignmentMap.values()){
			Collections.sort(lstAssign, new EffectiveDateTimestampCompare());
			Collections.reverse(lstAssign);
		}

		Date currDate = TKUtils.getCurrentDate();
		//Active = Both and Show History = Yes
		//return all results
		if(StringUtils.isEmpty(active) && StringUtils.equals("Y", showHistory)){
			return objectList;
		}
		//Active = Both and show history = No
		//return the most effective results from today and any future rows
		else if(StringUtils.isEmpty(active) && StringUtils.equals("N", showHistory)){
			for(List<Assignment> lstAssign : jobToAssignmentMap.values()){
				for(Assignment assign : lstAssign){
					if(assign.getEffectiveDate().before(currDate)){
						finalBusinessObjectList.add(assign);
						break;
					} else {
						finalBusinessObjectList.add(assign);
					}
				}
			}
		}
		//Active = Yes and Show History = No
		//return all active records as of today and any active future rows
		else if(StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)){
			for(List<Assignment> lstAssign : jobToAssignmentMap.values()){
				for(Assignment assign : lstAssign){
					if(assign.isActive()){
						if(assign.getEffectiveDate().before(currDate)){
							finalBusinessObjectList.add(assign);
							break;
						} else {
							finalBusinessObjectList.add(assign);
						}
					}
				}
			}
		}
		//Active = Yes and Show History = Yes
		//return all active records from database
		else if(StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)){
			for(List<Assignment> lstAssign : jobToAssignmentMap.values()){
				for(Assignment assign : lstAssign){
					if(assign.isActive()){
						finalBusinessObjectList.add(assign);
					}
				}
			}
		}
		//Active = No and Show History = Yes
		//return all inactive records in the database
		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")){
			for(List<Assignment> lstAssign : jobToAssignmentMap.values()){
				for(Assignment assign : lstAssign){
					if(!assign.isActive()){
						finalBusinessObjectList.add(assign);
					}
				}
			}
		}
		//Active = No and Show History = No
		//return the most effective inactive rows if there are no active rows <= the curr date
		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")){
			for(List<Assignment> lstAssign : jobToAssignmentMap.values()){
				for(Assignment assign : lstAssign){
					if(assign.getEffectiveDate().before(currDate)){
						if(!assign.isActive()){
							finalBusinessObjectList.add(assign);
						}
						break;
					} else {
						if(!assign.isActive()){
							finalBusinessObjectList.add(assign);
						}
					}
				}
			}
		}
		return finalBusinessObjectList;
	}

	@SuppressWarnings("rawtypes")
	public class EffectiveDateTimestampCompare implements Comparator{

		@Override
		public int compare(Object arg0, Object arg1) {
			Assignment assign = (Assignment)arg0;
			Assignment assign2 = (Assignment)arg1;
			int result = assign.getEffectiveDate().compareTo(assign2.getEffectiveDate());
			if(result==0){
				return assign.getTimestamp().compareTo(assign2.getTimestamp());
			}
			return result;
		}

	}

}
