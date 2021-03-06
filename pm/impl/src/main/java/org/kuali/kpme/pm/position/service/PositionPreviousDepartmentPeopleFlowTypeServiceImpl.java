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
package org.kuali.kpme.pm.position.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.util.xml.XmlHelper;
import org.kuali.rice.kew.api.document.Document;
import org.kuali.rice.kew.api.document.DocumentContent;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
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

public class PositionPreviousDepartmentPeopleFlowTypeServiceImpl extends DataDictionaryPeopleFlowTypeServiceImpl {
    private static final Logger LOG = Logger.getLogger(PositionPreviousDepartmentPeopleFlowTypeServiceImpl.class);
    @Override
    public List<Map<String, String>> resolveMultipleRoleQualifiers(
            @WebParam(name = "kewTypeId") String kewTypeId,
            @WebParam(name = "roleId") String roleId,
            @WebParam(name = "document") Document document,
            @WebParam(name = "documentContent") DocumentContent documentContent) {
        List<Map<String, String>> deptQualifiers = new ArrayList<Map<String, String>>();
        String department = getElementValue(documentContent.getApplicationContent(), "//document/oldMaintainableObject/businessObject/primaryDepartment/@value");
        String groupKeyCode = getElementValue(documentContent.getApplicationContent(), "//document/oldMaintainableObject/businessObject/groupKeyCode/@value");
        if (StringUtils.isNotEmpty(department)) {
            Map<String, String> qualifiers = new HashMap<String, String>();
            qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
            qualifiers.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKeyCode);
            deptQualifiers.add(qualifiers);

        } else {
            //try to get values from maintainable object if instance of position
            try {
                org.kuali.rice.krad.document.Document doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(document.getDocumentId());
                if (doc instanceof MaintenanceDocument) {
                    MaintenanceDocument md =  (MaintenanceDocument)doc;
                    if (md.getOldMaintainableObject().getDataObject() instanceof PositionBo) {
                        PositionBo position = (PositionBo)(md.getOldMaintainableObject().getDataObject());
                        Map<String, String> qualifiers = new HashMap<String, String>();
                        qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), position.getPrimaryDepartment());
                        qualifiers.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), position.getGroupKeyCode());
                        deptQualifiers.add(qualifiers);
                    }
                }
            } catch (WorkflowException e) {
                LOG.error("Unable to retrieve document with documemnt ID: " + document.getDocumentId());
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
