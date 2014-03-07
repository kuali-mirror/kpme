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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartmentContract;
import org.kuali.kpme.pm.position.Position;
import org.kuali.kpme.pm.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PositionAdditionalDepartmentsPeopleFlowTypeServiceImpl extends DataDictionaryPeopleFlowTypeServiceImpl {
    private static final Logger LOG = Logger.getLogger(PositionAdditionalDepartmentsPeopleFlowTypeServiceImpl.class);

    @Override
    public List<Map<String, String>> resolveMultipleRoleQualifiers(
            @WebParam(name = "kewTypeId") String kewTypeId,
            @WebParam(name = "roleId") String roleId,
            @WebParam(name = "document") Document document,
            @WebParam(name = "documentContent") DocumentContent documentContent) {
        List<Map<String, String>> deptQualifiers = new ArrayList<Map<String, String>>();
            //try to get values from maintainable object if instance of position
            try {
                org.kuali.rice.krad.document.Document doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(document.getDocumentId());
                if (doc instanceof MaintenanceDocument) {
                    MaintenanceDocument md = (MaintenanceDocument) doc;
                    if (md.getNewMaintainableObject().getDataObject() instanceof Position) {
                        Position position = (Position) (md.getNewMaintainableObject().getDataObject());

                        for (PositionDepartment positionDepartment : position.getDepartmentList()) {
                            if (!positionDepartment.getDeptAfflObj().isPrimaryIndicator()) {
                                deptQualifiers.add(
                                        Collections.singletonMap(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), String.valueOf(positionDepartment.getDepartment())));
                            }
                        }
                    }
                } else {
                    // If doc itself is instance of Position
                    if (doc instanceof Position) {
                        for (PositionDepartment positionDepartment : ((Position)doc).getDepartmentList()) {
                            if (!positionDepartment.getDeptAfflObj().isPrimaryIndicator()) {
                                deptQualifiers.add(
                                        Collections.singletonMap(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), String.valueOf(positionDepartment.getDepartment())));
                            }
                        }
                    }
                }
            } catch (WorkflowException e) {
                LOG.error("Unable to retrieve document with documemnt ID: " + document.getDocumentId());
            }


        if (deptQualifiers.isEmpty()) {deptQualifiers.add(Collections.singletonMap(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(),""));}
        return deptQualifiers;

    }

    @Override
    public Map<String, String> resolveRoleQualifiers(@WebParam(name = "kewTypeId") String kewTypeId,
                                                     @WebParam(name = "roleId") String roleId, @WebParam(name = "document") Document document,
                                                     @WebParam(name = "documentContent") DocumentContent documentContent) {
        return null;
    }
}
