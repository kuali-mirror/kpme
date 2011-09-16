package org.kuali.hr.time.docsearch;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.workarea.WorkArea;

public class TkSearchableAttributeServiceImpl implements
		TkSearchableAttributeService {

	@Override
	public String createSearchableAttributeXml(TimesheetDocument document) {
		List<Long> workAreas = new ArrayList<Long>();
		Map<String,List<Long>> deptToListOfWorkAreas = new HashMap<String,List<Long>>();
		List<String> salGroups = new ArrayList<String>();
		Date asOfDate = document.getAsOfDate();
		
		for(Assignment assign: document.getAssignments()){
			if(!workAreas.contains(assign.getWorkArea())){
				workAreas.add(assign.getWorkArea());
			}
			Job job = TkServiceLocator.getJobSerivce().getJob(assign.getPrincipalId(), assign.getJobNumber(), document.getAsOfDate());
			
			if(!salGroups.contains(job.getHrSalGroup())){
				salGroups.add(job.getHrSalGroup());
			}
		}
		
		for(Long workArea : workAreas){
			WorkArea workAreaObj = TkServiceLocator.getWorkAreaService().getWorkArea(workArea, asOfDate);
			if(deptToListOfWorkAreas.containsKey(workAreaObj.getDept())){
				List<Long> deptWorkAreas = deptToListOfWorkAreas.get(workAreaObj.getDept());
				deptWorkAreas.add(workArea);
			} else {
				List<Long> deptWorkAreas = new ArrayList<Long>();
				deptWorkAreas.add(workArea);
				deptToListOfWorkAreas.put(workAreaObj.getDept(), deptWorkAreas);
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<documentContext><applicationContent><TimesheetDocument>");
		sb.append("<DEPARTMENTS>");
		for(String dept : deptToListOfWorkAreas.keySet()){
			sb.append("<DEPARTMENT value=\""+dept+"\">");
			List<Long> deptWorkAreas = deptToListOfWorkAreas.get(dept);
			for(Long workArea : deptWorkAreas){
				sb.append("<WORKAREA value=\""+workArea+"\"/>");
			}
			sb.append("</DEPARTMENT>");
		}
		sb.append("</DEPARTMENTS>");
		for(String salGroup : salGroups){
			sb.append("<SALGROUP value=\""+salGroup+"\"/>");
		}
		
		sb.append("<PAYENDDATE value=\""+document.getPayCalendarEntry().getEndPeriodDate()+"\"/>");
		sb.append("</TimesheetDocument></applicationContent></documentContext>");
		
		return sb.toString();
	}

}
