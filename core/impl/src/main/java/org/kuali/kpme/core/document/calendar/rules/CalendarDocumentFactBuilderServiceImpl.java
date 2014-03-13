/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.document.calendar.rules;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.core.krms.KpmeKrmsFactBuilderServiceHelper;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.util.xml.XmlHelper;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.Term;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;


public class CalendarDocumentFactBuilderServiceImpl extends KpmeKrmsFactBuilderServiceHelper {

    @Override
    public void addFacts(Facts.Builder factsBuilder, String docContent) {
      //not supported yet
    }

    @Override
    public void addFacts(Facts.Builder factsBuilder, Object factsObject) {
        addFacts(factsBuilder, factsObject, "KPME-TK-CONTEXT", "KPME_TIMESHEET");
    }


    public void addFacts(Facts.Builder factsBuilder, Object factsObject, String contextId, String namespace) {
        CalendarDocument document = (CalendarDocument)factsObject;
        addObjectMembersAsFacts(factsBuilder, document, contextId, namespace);
        factsBuilder.addFact(new Term("payrollProcessorApproval"), Boolean.FALSE);
        if (document != null) {
            CalendarEntryContract ce = document.getCalendarEntry();
            LocalDate asOfDate = ce != null ? ce.getEndPeriodLocalDateTime().toLocalDate() : LocalDate.now();
            Set<String> workAreas = new HashSet<String>();
            Set<String> depts = new HashSet<String>();

            for (Assignment a : document.getAssignments()) {
                workAreas.add(String.valueOf(a.getWorkArea()));
                depts.add(a.getDept());
                Department department = HrServiceLocator.getDepartmentService().getDepartment(a.getDept(), asOfDate);
                if (department != null
                        && department.isPayrollApproval()) {
                    factsBuilder.addFact(new Term("payrollProcessorApproval"), Boolean.TRUE);
                }
            }
            if (CollectionUtils.isNotEmpty(depts)) {
                factsBuilder.addFact("department", depts);
            }
            if (CollectionUtils.isNotEmpty(workAreas)) {
                factsBuilder.addFact("workarea", workAreas);
            }

            if (document instanceof Assignable) {
                factsBuilder.addFact(Assignable.ASSIGNABLE_TERM_NAME, document);
            }
        }
    }

    protected String getElementValue(String docContent, String xpathExpression) {
        try {
            Document document = XmlHelper.trimXml(new ByteArrayInputStream(docContent.getBytes()));

            XPath xpath = XPathHelper.newXPath();
            String value = (String) xpath.evaluate(xpathExpression, document, XPathConstants.STRING);

            return value;

        } catch (Exception e) {
            throw new RiceRuntimeException(e);
        }
    }
}
