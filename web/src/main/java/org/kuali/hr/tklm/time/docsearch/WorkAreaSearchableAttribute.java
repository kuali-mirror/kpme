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
package org.kuali.hr.tklm.time.docsearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.document.DocumentWithContent;
import org.kuali.rice.kew.api.extension.ExtensionDefinition;
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
    public List extractDocumentAttributes(ExtensionDefinition extensionDefinition, DocumentWithContent documentWithContent) {
		List<SearchableAttributeValue> searchableAttributeValues = new ArrayList<SearchableAttributeValue>();
		
		String documentId = StringUtils.substringBetween(documentWithContent.getDocumentContent().toString(), "<string>", "</string>");
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
