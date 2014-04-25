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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;

public class KPMEHrGroupKeyedBusinessObjectLookupDaoOjbImpl extends KpmeHrBusinessObjectLookupDaoOjbImpl {

	private static final String INSTITUTION_PARAM_NAME = "institutionCode";
	private static final String LOCATION_PARAM_NAME = "locationId";
	private static final String GRP_KEY_PARAM_NAME = "groupKeyCode";
	
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(KPMEHrGroupKeyedBusinessObjectLookupDaoOjbImpl.class);
	
	@SuppressWarnings("rawtypes")
	protected Map<String, String> removeAndTransformFormProperties(Class<? extends HrBusinessObjectContract> businessObjectClass, Map formProps) {
		HashMap<String, String> retVal = new HashMap<String, String>();
		// remove the institution and location field from the form map and add them to the return value map
		String institution = (String) formProps.remove(INSTITUTION_PARAM_NAME);
		if(institution != null) {
			retVal.put(INSTITUTION_PARAM_NAME, institution);
		}
		String location = (String) formProps.remove(LOCATION_PARAM_NAME);
		if(location != null) {
			retVal.put(LOCATION_PARAM_NAME, location);
		}
		// call super class method
		retVal.putAll(super.removeAndTransformFormProperties(businessObjectClass, formProps));
		return retVal;
	}
	
	
	// inject effective dated subqueries into the root criteria if show history is not chosen
	@SuppressWarnings("rawtypes")
	protected void processRootCriteria(Criteria rootCriteria, Class<? extends HrBusinessObjectContract> businessObjectClass, Map formProps, Map<String, String> removedProperties) {
		// call super class method
		super.processRootCriteria(rootCriteria, businessObjectClass, formProps, removedProperties);
		// inject subquery for group key based on user-entered location and institution
		String institutionVal = removedProperties.get(INSTITUTION_PARAM_NAME);
		String locationVal = removedProperties.get(LOCATION_PARAM_NAME);
		if(StringUtils.isNotBlank(institutionVal) || StringUtils.isNotBlank(locationVal)) {
			// create the subquery criteria and conditionally add 'like' checks for institution and location
			Criteria subQueryCriteria = new Criteria();
			if(StringUtils.isNotBlank(institutionVal)) {
				subQueryCriteria.addLike(INSTITUTION_PARAM_NAME, institutionVal);
			}		
			if(StringUtils.isNotBlank(locationVal)) {
				subQueryCriteria.addLike(LOCATION_PARAM_NAME, locationVal);
			}
			// create the subquery for the grp key BO's table using the above criteria, and set the 'select' to be the grp key code column
			ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(HrGroupKeyBo.class, subQueryCriteria);
			subQuery.setAttributes(new String[]{GRP_KEY_PARAM_NAME});
			// finally constrain the root criteria to grp key codes only within those found by the subquery
			rootCriteria.addIn(GRP_KEY_PARAM_NAME, subQuery);
		}
	}	
}