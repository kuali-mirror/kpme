package org.kuali.hr.time.docsearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.docsearch.DocumentSearchContext;
import org.kuali.rice.kew.docsearch.SearchableAttributeLongValue;
import org.kuali.rice.kew.docsearch.SearchableAttributeValue;
import org.kuali.rice.kew.docsearch.xml.StandardGenericXMLSearchableAttribute;

public class WorkAreaSearchableAttribute extends StandardGenericXMLSearchableAttribute{

	private static String WORK_AREA = "workArea";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public List getSearchStorageValues(DocumentSearchContext docContext) {
		List<SearchableAttributeValue> searchableAttributeValues = new ArrayList<SearchableAttributeValue>();
		
		String documentId = StringUtils.substringBetween(docContext.getDocumentContent(), "<string>", "</string>");
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
		List<Long> workAreasIncluded = new ArrayList<Long>();
		for(Assignment assign : timesheetDocument.getAssignments()){
			if(!workAreasIncluded.contains(assign.getWorkArea())){
				SearchableAttributeValue attValue = new SearchableAttributeLongValue();
				attValue.setSearchableAttributeKey(WORK_AREA);
				attValue.setupAttributeValue(StringUtils.upperCase(assign.getWorkArea().toString()));
				searchableAttributeValues.add(attValue);
				workAreasIncluded.add(assign.getWorkArea());
			}
		}
		return searchableAttributeValues;
	}
}
