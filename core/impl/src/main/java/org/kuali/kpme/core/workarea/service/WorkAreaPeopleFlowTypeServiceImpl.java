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
package org.kuali.kpme.core.workarea.service;

import org.kuali.kpme.core.KPMEAttributes;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.uif.RemotableAttributeError;
import org.kuali.rice.core.api.uif.RemotableAttributeField;
import org.kuali.rice.core.api.util.jaxb.MapStringStringAdapter;
import org.kuali.rice.core.api.util.xml.XmlHelper;
import org.kuali.rice.kew.api.document.Document;
import org.kuali.rice.kew.api.document.DocumentContent;
import org.kuali.rice.kew.framework.peopleflow.PeopleFlowTypeService;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.krad.workflow.DataDictionaryPeopleFlowTypeServiceImpl;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.jws.WebParam;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jjhanso
 * Date: 7/8/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkAreaPeopleFlowTypeServiceImpl extends DataDictionaryPeopleFlowTypeServiceImpl {
    /*@Override
    public List<String> filterToSelectableRoleIds(@WebParam(name = "kewTypeId") String kewTypeId,
                                                  @WebParam(name = "roleIds") List<String> roleIds) {
        return roleIds;
    }*/

    @Override
    public List<Map<String, String>> resolveMultipleRoleQualifiers(
            @WebParam(name = "kewTypeId") String kewTypeId,
            @WebParam(name = "roleId") String roleId,
            @WebParam(name = "document") Document document,
            @WebParam(name = "documentContent") DocumentContent documentContent) {
        List<Map<String, String>> workAreaQualifiers = new ArrayList<Map<String, String>>();
        List<String> workAreas = getElementValues(documentContent.getApplicationContent(), "//WORKAREA/@value");
        for (String workArea : workAreas) {
            workAreaQualifiers.add(
                    Collections.singletonMap(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(),
                    workArea));
        }

        return workAreaQualifiers;
        //documentContent.getSearchableContent()

    }


    @Override
    public Map<String, String> resolveRoleQualifiers(@WebParam(name = "kewTypeId") String kewTypeId,
            @WebParam(name = "roleId") String roleId, @WebParam(name = "document") Document document,
            @WebParam(name = "documentContent") DocumentContent documentContent) {
        return null;
    }

    /*@Override
    public List<RemotableAttributeField> getAttributeFields(@WebParam(name = "kewTypeId") String kewTypeId) {
        return Collections.singletonList(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName());
    }*/

    /*@Override
    public List<RemotableAttributeError> validateAttributes(@WebParam(name = "kewTypeId") String kewTypeId,
            @WebParam(name = "attributes") @XmlJavaTypeAdapter(MapStringStringAdapter.class) Map<String, String> attributes) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }*/

    /*@Override
    public List<RemotableAttributeError> validateAttributesAgainstExisting(
            @WebParam(name = "kewTypeId") String kewTypeId,
            @WebParam(name = "newAttributes")
            @XmlJavaTypeAdapter(MapStringStringAdapter.class) Map<String, String> newAttributes,
            @WebParam(name = "oldAttributes")
            @XmlJavaTypeAdapter(MapStringStringAdapter.class) Map<String, String> oldAttributes) throws RiceIllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }*/

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
