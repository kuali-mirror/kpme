package org.kuali.hr.time.docsearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.service.WorkflowDocument;

public class TkSearchableAttributeServiceImpl implements
		TkSearchableAttributeService {

    private static final Logger LOG = Logger.getLogger(TkSearchableAttributeServiceImpl.class);

	public void updateSearchableAttribute(TimesheetDocument document, Date asOfDate){
        WorkflowDocument workflowDocument = null;
        //
        // djunk - Need to actually look at why this call is here for every
        //         document submission. Rice does not allow save events for
        //         documents in final status. We may add more skips.
        //
        if (!document.getDocumentHeader().getDocumentStatus().equals("F")) {
            try {
                workflowDocument = new WorkflowDocument(document.getPrincipalId(), Long.parseLong(document.getDocumentId()));
                workflowDocument.setApplicationContent(createSearchableAttributeXml(document, asOfDate));
                workflowDocument.saveDocument("");
                if (!"I".equals(workflowDocument.getRouteHeader().getDocRouteStatus())) {
                    //updateWorkflowTitle(document,workflowDocument);
                    if(workflowDocument.getRouteHeader().getInitiatorPrincipalId().equals(TKContext.getPrincipalId())){
                        workflowDocument.saveDocument("");
                    }else{
                        workflowDocument.saveRoutingData();
                    }
                }else{
                    workflowDocument.saveDocument("");
                }


            } catch (Exception e) {
                LOG.warn("Exception during searchable attribute update.");
                throw new RuntimeException(e);
            }
        }
	}

	@Override
	public String createSearchableAttributeXml(TimesheetDocument document, Date asOfDate) {
		List<Long> workAreas = new ArrayList<Long>();
		Map<String,List<Long>> deptToListOfWorkAreas = new HashMap<String,List<Long>>();
		List<String> salGroups = new ArrayList<String>();

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
			WorkArea workAreaObj = TkServiceLocator.getWorkAreaService().getWorkArea(workArea, TKUtils.getTimelessDate(asOfDate));
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

		sb.append("<PAYENDDATE value=\""+asOfDate+"\"/>");
		sb.append("</TimesheetDocument></applicationContent></documentContext>");

		return sb.toString();
	}

}
