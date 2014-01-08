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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.dao.impl.LookupDaoOjb;

public class HrBusinessObjectLookupDaoOjbImpl extends LookupDaoOjb {

	private static final String DOES_NOT_CONTAIN_BUSINESS_KEYS_MESSAGE = " does not contain a BUSINESS_KEYS list";
	private static final String TIMESTAMP = "timestamp";
	private static final String BUSINESS_KEYS_VAR_NAME = "BUSINESS_KEYS";
	private static final String EFFECTIVE_DATE = "effectiveDate";
	private static final String NO = "N";
	private static final String HISTORY_PARAM_NAME = "history";
	
	private static final Logger LOG = Logger.getLogger(HrBusinessObjectLookupDaoOjbImpl.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Criteria getCollectionCriteriaFromMap(BusinessObject example, Map formProps) {
		// read and remove the history option from the form map
		String showHistory = (String) formProps.remove(HISTORY_PARAM_NAME);
		Criteria returnVal = super.getCollectionCriteriaFromMap(example, formProps);
		// inject the efft date and timestamp subqueries if history is not to be shown
		if (StringUtils.equals(showHistory, NO)) {
			injectSubQueries(returnVal,	(Class<? extends HrBusinessObjectContract>) example.getClass(), formProps);
		}
		return returnVal;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Criteria getCollectionCriteriaFromMapUsingPrimaryKeysOnly(Class businessObjectClass, Map formProps) {
		 // read and remove the history option from the form map
		 String showHistory = (String) formProps.remove(HISTORY_PARAM_NAME);
		 Criteria returnVal = super.getCollectionCriteriaFromMapUsingPrimaryKeysOnly(businessObjectClass, formProps);
		 // inject the efft date and timestamp subqueries if history is not to be shown
		 if (StringUtils.equals(showHistory, NO)) {
			 injectSubQueries(returnVal, (Class<? extends HrBusinessObjectContract>) businessObjectClass, formProps);
		 }
		 return returnVal;
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