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
package org.kuali.hr.core;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.web.format.Formatter;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.LookupUtils;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.bo.DataObjectRelationship;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.datadictionary.AttributeSecurity;
import org.kuali.rice.krad.util.ExternalizableBusinessObjectUtils;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;

public class TkInquirableImpl extends KualiInquirableImpl {
	
	private static final Logger LOG = Logger.getLogger(TkInquirableImpl.class);
	
	@Override
	// copied the getInquiryUrl() from KualiInquirableImpl and added effectiveDate to parameters
    public HtmlData getInquiryUrl(BusinessObject businessObject, String attributeName, boolean forceInquiry) {
        Properties parameters = new Properties();
        AnchorHtmlData hRef = new AnchorHtmlData(KRADConstants.EMPTY_STRING, KRADConstants.EMPTY_STRING);
        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");

        Class inquiryBusinessObjectClass = null;
        String attributeRefName = "";
        boolean isPkReference = false;

        boolean doesNestedReferenceHaveOwnPrimitiveReference = false;
        BusinessObject nestedBusinessObject = null;

        if (attributeName.equals(getBusinessObjectDictionaryService().getTitleAttribute(businessObject.getClass()))) {
            inquiryBusinessObjectClass = businessObject.getClass();
            isPkReference = true;
        }
        else {
            if (ObjectUtils.isNestedAttribute(attributeName)) {
                // if we have a reference object, we should determine if we should either provide an inquiry link to
                // the reference object itself, or some other nested primitive.

                // for example, if the attribute is "referenceObject.someAttribute", and there is no primitive reference for
                // "someAttribute", then an inquiry link is provided to the "referenceObject".  If it does have a primitive reference, then
                // the inquiry link is directed towards it instead
                String nestedReferenceName = ObjectUtils.getNestedAttributePrefix(attributeName);
                Object nestedReferenceObject = ObjectUtils.getNestedValue(businessObject, nestedReferenceName);

                if (ObjectUtils.isNotNull(nestedReferenceObject) && nestedReferenceObject instanceof BusinessObject) {
                    nestedBusinessObject = (BusinessObject) nestedReferenceObject;
                    String nestedAttributePrimitive = ObjectUtils.getNestedAttributePrimitive(attributeName);

                    if (nestedAttributePrimitive.equals(getBusinessObjectDictionaryService().getTitleAttribute(nestedBusinessObject.getClass()))) {
                    	// we are going to inquiry the record that contains the attribute we're rendering an inquiry URL for
                    	inquiryBusinessObjectClass = nestedBusinessObject.getClass();
                        // I know it's already set to false, just to show how this variable is set
                        doesNestedReferenceHaveOwnPrimitiveReference = false;
                    }
                    else {
	                    Map primitiveReference = LookupUtils.getPrimitiveReference(nestedBusinessObject, nestedAttributePrimitive);
	                    if (primitiveReference != null && !primitiveReference.isEmpty()) {
	                        attributeRefName = (String) primitiveReference.keySet().iterator().next();
	                        inquiryBusinessObjectClass = (Class) primitiveReference.get(attributeRefName);
	                        doesNestedReferenceHaveOwnPrimitiveReference = true;
	                    }
	                    else {
	                    	// we are going to inquiry the record that contains the attribute we're rendering an inquiry URL for
	                		inquiryBusinessObjectClass = nestedBusinessObject.getClass();
	                        // I know it's already set to false, just to show how this variable is set
	                        doesNestedReferenceHaveOwnPrimitiveReference = false;
	                    }
                    }
                }
            }
            else {
                Map primitiveReference = LookupUtils.getPrimitiveReference(businessObject, attributeName);
                if (primitiveReference != null && !primitiveReference.isEmpty()) {
                    attributeRefName = (String) primitiveReference.keySet().iterator().next();
                    inquiryBusinessObjectClass = (Class) primitiveReference.get(attributeRefName);
                }
            }
        }

        if (inquiryBusinessObjectClass != null && DocumentHeader.class.isAssignableFrom(inquiryBusinessObjectClass)) {
            String documentNumber = (String) ObjectUtils.getPropertyValue(businessObject, attributeName);
            if (!StringUtils.isBlank(documentNumber)) {
                // if NullPointerException on the following line, maybe the Spring bean wasn't injected w/ KualiConfigurationException, or if
                // instances of a sub-class of this class are not Spring created, then override getKualiConfigurationService() in the subclass
                // to return the configuration service from a Spring service locator (or set it).
                hRef.setHref(getKualiConfigurationService().getPropertyValueAsString(KRADConstants.WORKFLOW_URL_KEY) + KRADConstants.DOCHANDLER_DO_URL + documentNumber + KRADConstants.DOCHANDLER_URL_CHUNK);
            }
            return hRef;
        }

        if (inquiryBusinessObjectClass == null || getBusinessObjectDictionaryService().isInquirable(inquiryBusinessObjectClass) == null || !getBusinessObjectDictionaryService().isInquirable(inquiryBusinessObjectClass).booleanValue()) {
            return hRef;
        }

        synchronized (SUPER_CLASS_TRANSLATOR_LIST) {
            for (Class clazz : SUPER_CLASS_TRANSLATOR_LIST) {
                if (clazz.isAssignableFrom(inquiryBusinessObjectClass)) {
                    inquiryBusinessObjectClass = clazz;
                    break;
                }
            }
        }

        if (!inquiryBusinessObjectClass.isInterface() && ExternalizableBusinessObject.class.isAssignableFrom(inquiryBusinessObjectClass)) {
        	inquiryBusinessObjectClass = ExternalizableBusinessObjectUtils.determineExternalizableBusinessObjectSubInterface(inquiryBusinessObjectClass);
        }

        parameters.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, inquiryBusinessObjectClass.getName());


        // listPrimaryKeyFieldNames returns an unmodifiable list.  So a copy is necessary.
        List<String> keys = new ArrayList<String>(getBusinessObjectMetaDataService().listPrimaryKeyFieldNames(inquiryBusinessObjectClass));

        if (keys == null) {
        	keys = Collections.emptyList();
        }

        DataObjectRelationship businessObjectRelationship = null;

        if(attributeRefName != null && !"".equals(attributeRefName)){
	        businessObjectRelationship =
	    		getBusinessObjectMetaDataService().getBusinessObjectRelationship(
	    				businessObject, attributeRefName);

	    	if (businessObjectRelationship != null && businessObjectRelationship.getParentToChildReferences() != null) {
		    	for (String targetNamePrimaryKey : businessObjectRelationship.getParentToChildReferences().values()) {
		    		keys.add(targetNamePrimaryKey);
		    	}
	    	}
        }
        // build key value url parameters used to retrieve the business object
        String keyName = null;
        String keyConversion = null;
        Map<String, String> fieldList = new HashMap<String,String>();
        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            keyName = (String) iter.next();
            keyConversion = keyName;
            if (ObjectUtils.isNestedAttribute(attributeName)) {
                if (doesNestedReferenceHaveOwnPrimitiveReference) {
                    String nestedAttributePrefix = ObjectUtils.getNestedAttributePrefix(attributeName);
                    //String foreignKeyFieldName = getBusinessObjectMetaDataService().getForeignKeyFieldName(
                    //        inquiryBusinessObjectClass.getClass(), attributeRefName, keyName);

                    String foreignKeyFieldName = getBusinessObjectMetaDataService().getForeignKeyFieldName(
                    								nestedBusinessObject.getClass(), attributeRefName, keyName);
                    keyConversion = nestedAttributePrefix + "." + foreignKeyFieldName;
                }
                else {
                    keyConversion = ObjectUtils.getNestedAttributePrefix(attributeName) + "." + keyName;
                }
            }
            else {
                if (isPkReference) {
                    keyConversion = keyName;
                }
                else if (businessObjectRelationship != null) {
                	//Using BusinessObjectMetaDataService instead of PersistenceStructureService
                	//since otherwise, relationship information from datadictionary is not used at all
                	//Also, BOMDS.getBusinessObjectRelationship uses PersistenceStructureService,
                	//so both datadictionary and the persistance layer get covered
                	/*
                	BusinessObjectRelationship businessObjectRelationship =
                		getBusinessObjectMetaDataService().getBusinessObjectRelationship(
                				businessObject, attributeRefName);
                				*/
                	BidiMap bidiMap = new DualHashBidiMap(businessObjectRelationship.getParentToChildReferences());
                	keyConversion = (String)bidiMap.getKey(keyName);
                    //keyConversion = getPersistenceStructureService().getForeignKeyFieldName(businessObject.getClass(), attributeRefName, keyName);
                }
            }
            Object keyValue = null;
            if (keyConversion != null) {
                keyValue = ObjectUtils.getPropertyValue(businessObject, keyConversion);
            }

            if (keyValue == null) {
                keyValue = "";
            } else if (keyValue instanceof Date) { //format the date for passing in url
                if (Formatter.findFormatter(keyValue.getClass()) != null) {
                    Formatter formatter = Formatter.getFormatter(keyValue.getClass());
                    keyValue = (String) formatter.format(keyValue);
                }
            } else {
                keyValue = keyValue.toString();
            }

            // Encrypt value if it is a field that has restriction that prevents a value from being shown to user,
            // because we don't want the browser history to store the restricted attribute's value in the URL
            AttributeSecurity attributeSecurity = KNSServiceLocator.getDataDictionaryService().getAttributeSecurity(businessObject.getClass().getName(), keyName);
            if(attributeSecurity != null && attributeSecurity.hasRestrictionThatRemovesValueFromUI()){
            	try {
                    keyValue = getEncryptionService().encrypt(keyValue);
                }
                catch (GeneralSecurityException e) {
                    LOG.error("Exception while trying to encrypted value for inquiry framework.", e);
                    throw new RuntimeException(e);
                }
            }

            parameters.put(keyName, keyValue);
            fieldList.put(keyName, keyValue.toString());
        }
        
        // pass in effective date of the businessObject
        Date aDate = (Date) ObjectUtils.getPropertyValue(businessObject, "effectiveDate");
        if(aDate != null) {
        	parameters.put("effectiveDate", new SimpleDateFormat("MM/dd/yyyy").format(aDate));
        }
                	
        return getHyperLink(inquiryBusinessObjectClass, fieldList, UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, parameters));
    }
}