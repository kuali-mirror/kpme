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
package org.kuali.kpme.core.bo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.lookup.WildcardableAttributeDefinition;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.search.SearchOperator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.dao.impl.LookupDaoOjb;
import org.kuali.rice.krad.datadictionary.AttributeDefinition;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

public class KpmeHrBusinessObjectLookupDaoOjbImpl extends LookupDaoOjb {

	private static final String DOES_NOT_CONTAIN_BUSINESS_KEYS_MESSAGE = " does not contain a BUSINESS_KEYS list";
	private static final String TIMESTAMP = "timestamp";
	private static final String BUSINESS_KEYS_VAR_NAME = "BUSINESS_KEYS";
	private static final String EFFECTIVE_DATE = "effectiveDate";
	private static final String NO = "N";
	private static final String HISTORY_PARAM_NAME = "history";
	
	private static final Logger LOG = Logger.getLogger(KpmeHrBusinessObjectLookupDaoOjbImpl.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Criteria getCollectionCriteriaFromMap(BusinessObject example, Map formProps) {
		Map<String, String> removedProperties = removeAndTransformFormProperties((Class<? extends HrBusinessObjectContract>) example.getClass(), formProps);		
		Criteria rootCriteria = super.getCollectionCriteriaFromMap(example, formProps);		
		processRootCriteria(rootCriteria, (Class<? extends HrBusinessObjectContract>) example.getClass(), formProps, removedProperties);
		return rootCriteria;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Criteria getCollectionCriteriaFromMapUsingPrimaryKeysOnly(Class businessObjectClass, Map formProps) {
		Map<String, String> removedProperties = removeAndTransformFormProperties(businessObjectClass, formProps);		 
		Criteria rootCriteria = super.getCollectionCriteriaFromMapUsingPrimaryKeysOnly((Class<? extends HrBusinessObjectContract>) businessObjectClass, formProps);		 
		processRootCriteria(rootCriteria, (Class<? extends HrBusinessObjectContract>) businessObjectClass, formProps, removedProperties);
		return rootCriteria;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void transformWildCardableFields(Class<? extends HrBusinessObjectContract> hrBOClass, Map formProps) {
		Iterator propsIter = formProps.keySet().iterator();
        while (propsIter.hasNext()) {
            String propertyName = (String) propsIter.next();
            String propertyValue = (String) formProps.get(propertyName);
            if (StringUtils.isNotBlank(propertyValue)) {
            	// transform this value into an "OR" with the wildcard symbols
            	boolean canContainWildcard = false;
            	if (KRADServiceLocatorWeb.getDataDictionaryService().isAttributeDefined(hrBOClass, propertyName)) {
            		AttributeDefinition attributeDefinition = KRADServiceLocatorWeb.getDataDictionaryService().getAttributeDefinition(hrBOClass.getName(), propertyName);
            		// check if this property is wildcarded
            		if (attributeDefinition instanceof WildcardableAttributeDefinition) {
            			canContainWildcard = ((WildcardableAttributeDefinition) attributeDefinition).getContainsWildcardData();
            		}
            		if (canContainWildcard) {
                		// get the wildcard symbols and attach them with an "OR" seperator i.e. "|"
                		List<String> wildcardStrings = ((WildcardableAttributeDefinition) attributeDefinition).getAllowedWildcardStrings();
                		if(wildcardStrings != null) {
	                		for(String wildcardString : wildcardStrings) {
	                			if( StringUtils.equals("*", wildcardString) || StringUtils.equals("%", wildcardString) ){
	                				wildcardString = "\\" + wildcardString;
	                			}
	                			propertyValue = propertyValue + SearchOperator.OR.op() + wildcardString;                			
	                		}
	                		formProps.put(propertyName, propertyValue);
                		}
            		}
            	}            	
            }
        }
	}

	
	@SuppressWarnings("rawtypes")
	protected Map<String, String> removeAndTransformFormProperties(Class<? extends HrBusinessObjectContract> businessObjectClass, Map formProps) {
		HashMap<String, String> retVal = new HashMap<String, String>();
		// remove the history option field from the form map and add it to the return value map
		retVal.put(HISTORY_PARAM_NAME, (String) formProps.remove(HISTORY_PARAM_NAME));
		// covert any field inputs to be ORed with wild cards if applicable
		transformWildCardableFields(businessObjectClass, formProps);
		return retVal;
	}
	
	
	// inject effective dated subqueries into the root criteria if show history is not chosen
	@SuppressWarnings("rawtypes")
	protected void processRootCriteria(Criteria rootCriteria, Class<? extends HrBusinessObjectContract> businessObjectClass, Map formProps, Map<String, String> removedProperties) {
		// inject the efft date and timestamp subqueries if history is not to be shown
		if (StringUtils.equals(removedProperties.get(HISTORY_PARAM_NAME), NO)) {
			injectSubQueries(rootCriteria, businessObjectClass, formProps);
		}
	}	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void injectSubQueries(Criteria root, Class<? extends HrBusinessObjectContract> hrBOClass, Map formProps) {
		// create the effective date filter criteria
		Criteria effectiveDateFilter = new Criteria();
		LocalDate fromEffdt = TKUtils.formatDateString(TKUtils.getFromDateString((String) formProps.get(EFFECTIVE_DATE)));
		LocalDate toEffdt = TKUtils.formatDateString(TKUtils.getToDateString((String) formProps.get(EFFECTIVE_DATE)));
		if (fromEffdt != null) {
			effectiveDateFilter.addGreaterOrEqualThan(EFFECTIVE_DATE, fromEffdt.toDate());
		}
		if (toEffdt != null) {
			effectiveDateFilter.addLessOrEqualThan(EFFECTIVE_DATE,	toEffdt.toDate());
		}
		if (fromEffdt == null && toEffdt == null) {
			effectiveDateFilter.addLessOrEqualThan(EFFECTIVE_DATE, LocalDate.now().toDate());
		}

		List<String> businessKeys = new ArrayList<String>();
		try {
			businessKeys = (List<String>) hrBOClass.getDeclaredField(BUSINESS_KEYS_VAR_NAME).get(hrBOClass);
		} 
		catch (NoSuchFieldException e) {
			LOG.warn(hrBOClass.getName() + DOES_NOT_CONTAIN_BUSINESS_KEYS_MESSAGE);
		} 
		catch (IllegalAccessException e) {
			LOG.warn(hrBOClass.getName() + DOES_NOT_CONTAIN_BUSINESS_KEYS_MESSAGE);
		}
		
	    // inject the subqueries
		root.addEqualTo(EFFECTIVE_DATE, OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(hrBOClass, effectiveDateFilter, businessKeys, false));
		root.addEqualTo(TIMESTAMP, OjbSubQueryUtil.getTimestampSubQuery(hrBOClass, businessKeys, false));
	}


}