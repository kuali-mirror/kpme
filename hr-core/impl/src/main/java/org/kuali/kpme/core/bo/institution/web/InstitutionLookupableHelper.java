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
package org.kuali.kpme.core.bo.institution.web;

import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.bo.HrEffectiveDateActiveLookupableHelper;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class InstitutionLookupableHelper extends HrEffectiveDateActiveLookupableHelper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5277378871669021091L;

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		// TODO Auto-generated method stub
		return super.getSearchResults(fieldValues);
	}

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		// TODO Auto-generated method stub
		return super.getCustomActionUrls(businessObject, pkNames);
	}


}
