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
package org.kuali.kpme.core.payroll.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.util.xml.XmlHelper;
import org.kuali.rice.kew.api.document.Document;
import org.kuali.rice.kew.api.document.DocumentContent;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.workflow.DataDictionaryPeopleFlowTypeServiceImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.jws.WebParam;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.io.ByteArrayInputStream;
import java.util.*;

public class PayrollPeopleFlowTypeServiceImpl extends DataDictionaryPeopleFlowTypeServiceImpl {
    private static final Logger LOG = Logger.getLogger(PayrollPeopleFlowTypeServiceImpl.class);

    @Override
    public List<Map<String, String>> resolveMultipleRoleQualifiers(
            @WebParam(name = "kewTypeId") String kewTypeId,
            @WebParam(name = "roleId") String roleId,
            @WebParam(name = "document") Document document,
            @WebParam(name = "documentContent") DocumentContent documentContent) {
        List<Map<String, String>> deptQualifiers = new ArrayList<Map<String, String>>();
        String principalName = getElementValue(documentContent.getApplicationContent(), "//PRINCIPALNAME/@value");
        String calendarEntryId = getElementValue(documentContent.getApplicationContent(), "//CALENTRYID/@value");
        List<Assignment> assignments = new ArrayList<Assignment>();

        if (StringUtils.isNotEmpty(principalName)
                && StringUtils.isNotEmpty(calendarEntryId)) {
            String principalId = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName).getPrincipalId();
            CalendarEntry ce = HrServiceLocator.getCalendarEntryService().getCalendarEntry(calendarEntryId);
            if (document.getDocumentTypeName().equals("TimesheetDocument")) {
                assignments = HrServiceLocator.getAssignmentService().getAllAssignmentsByCalEntryForTimeCalendar(principalId, ce);
            } else {
                assignments = HrServiceLocator.getAssignmentService().getAllAssignmentsByCalEntryForLeaveCalendar(principalId, ce);
            }
        } else {
            //try to get values from maintainable object if instance of assignable
            try {
                org.kuali.rice.krad.document.Document doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(document.getDocumentId());
                if (doc instanceof MaintenanceDocument) {
                    MaintenanceDocument md =  (MaintenanceDocument)doc;
                    if (md.getNewMaintainableObject().getDataObject() instanceof Assignable) {
                        Assignable assignable = (Assignable)(md.getNewMaintainableObject().getDataObject());
                        assignments = assignable.getAssignments();
                    }
                } else {
                    // If doc itself is instance of Assignable
                    if (doc instanceof Assignable) {
                        assignments = ((Assignable)doc).getAssignments();
                    }
                }
            } catch (WorkflowException e) {
                LOG.error("Unable to retrieve document with documemnt ID: " + document.getDocumentId());
            }
        }

        if (CollectionUtils.isNotEmpty(assignments)) {
            for (Assignment ac : assignments) {
                Map<String, String> qualifiers = new HashMap<String, String>();
                qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), ac.getDept());
                qualifiers.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), ac.getGroupKeyCode());

                deptQualifiers.add(qualifiers);
            }
        }
        return deptQualifiers;

    }


    @Override
    public Map<String, String> resolveRoleQualifiers(@WebParam(name = "kewTypeId") String kewTypeId,
            @WebParam(name = "roleId") String roleId, @WebParam(name = "document") Document document,
            @WebParam(name = "documentContent") DocumentContent documentContent) {
        return null;
    }

    protected String getElementValue(String docContent, String xpathExpression) {
        try {
            org.w3c.dom.Document document = XmlHelper.trimXml(new ByteArrayInputStream(docContent.getBytes()));

            XPath xpath = XPathHelper.newXPath();
            return (String) xpath.evaluate(xpathExpression, document, XPathConstants.STRING);


        } catch (Exception e) {
            throw new RiceRuntimeException();
        }
    }

    protected List<String> getElementValues(String docContent, String xpathExpression) {
        try {
            org.w3c.dom.Document document = XmlHelper.trimXml(new ByteArrayInputStream(docContent.getBytes()));
            List<String> values = new ArrayList<String>();
            XPath xpath = XPathHelper.newXPath();
            NodeList nodeList = (NodeList)xpath.evaluate(xpathExpression, document, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                values.add(node.getNodeValue());
            }
            return values;

        } catch (Exception e) {
            throw new RiceRuntimeException();
        }
    }
}
