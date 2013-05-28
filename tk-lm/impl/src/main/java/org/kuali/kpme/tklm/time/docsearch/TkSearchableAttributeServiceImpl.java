/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.docsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.core.document.CalendarDocumentHeaderContract;
import org.kuali.kpme.core.document.calendar.CalendarDocumentContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.krad.util.GlobalVariables;

public class TkSearchableAttributeServiceImpl implements
		TkSearchableAttributeService {

    private static final Logger LOG = Logger.getLogger(TkSearchableAttributeServiceImpl.class);

	public void updateSearchableAttribute(CalendarDocumentContract document, LocalDate asOfDate){
        WorkflowDocument workflowDocument = null;
        //
        // djunk - Need to actually look at why this call is here for every
        //         document submission. Rice does not allow save events for
        //         documents in final status. We may add more skips.
        //
        if (!document.getDocumentHeader().getDocumentStatus().equals("F")) {
            try {
                CalendarDocumentHeaderContract docHeader = document.getDocumentHeader();
                workflowDocument = WorkflowDocumentFactory.loadDocument(docHeader.getPrincipalId(), docHeader.getDocumentId());
                workflowDocument.setApplicationContent(createSearchableAttributeXml(document, asOfDate));
                workflowDocument.saveDocument("");
                if (!"I".equals(workflowDocument.getStatus().getCode())) {
                    //updateWorkflowTitle(document,workflowDocument);
                    if (GlobalVariables.getUserSession() != null && workflowDocument.getInitiatorPrincipalId().equals(GlobalVariables.getUserSession().getPrincipalId())) {
                        workflowDocument.saveDocument("");
                    } else{
                    	workflowDocument.saveDocumentData();
                    }
                } else{
                    workflowDocument.saveDocument("");
                }


            } catch (Exception e) {
                LOG.warn("Exception during searchable attribute update.");
                throw new RuntimeException(e);
            }
        }
	}

	@Override
	public String createSearchableAttributeXml(CalendarDocumentContract document, LocalDate asOfDate) {
		List<Long> workAreas = new ArrayList<Long>();
		Map<String,List<Long>> deptToListOfWorkAreas = new HashMap<String,List<Long>>();
		List<String> salGroups = new ArrayList<String>();

		for(Assignment assign: document.getAssignments()){
			if(!workAreas.contains(assign.getWorkArea())){
				workAreas.add(assign.getWorkArea());
			}
			Job job = HrServiceLocator.getJobService().getJob(assign.getPrincipalId(), assign.getJobNumber(), assign.getEffectiveLocalDate());

			if(!salGroups.contains(job.getHrSalGroup())){
				salGroups.add(job.getHrSalGroup());
			}
		}

		for(Long workArea : workAreas){
			WorkArea workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, asOfDate);
			String department = workAreaObj != null ? workAreaObj.getDept() : null;
			
			if (department != null) {
				if(deptToListOfWorkAreas.containsKey(department)){
					List<Long> deptWorkAreas = deptToListOfWorkAreas.get(workAreaObj.getDept());
					deptWorkAreas.add(workArea);
				} else {
					List<Long> deptWorkAreas = new ArrayList<Long>();
					deptWorkAreas.add(workArea);
					deptToListOfWorkAreas.put(department, deptWorkAreas);
				}
			}
		}
		StringBuilder sb = new StringBuilder();
        String className = document.getClass().getSimpleName();
		sb.append("<documentContext><applicationContent><").append(className).append(">");
		sb.append("<DEPARTMENTS>");
		for(Map.Entry<String, List<Long>> entry : deptToListOfWorkAreas.entrySet()){
			sb.append("<DEPARTMENT value=\""+entry.getKey()+"\">");

			for(Long workArea : entry.getValue()){
				sb.append("<WORKAREA value=\""+workArea+"\"/>");
			}
			sb.append("</DEPARTMENT>");
		}
		sb.append("</DEPARTMENTS>");
		for(String salGroup : salGroups){
			sb.append("<SALGROUP value=\""+salGroup+"\"/>");
		}

		sb.append("<PAYENDDATE value=\""+asOfDate+"\"/>");
		sb.append("</").append(className).append("></applicationContent></documentContext>");

		return sb.toString();
	}

}
