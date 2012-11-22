/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.balancetransfer.service;

import java.util.List;
import java.util.Map;

import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class BalanceTransferLookupableHelper extends
		HrEffectiveDateActiveLookupableHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6910172165048825489L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		return super.getCustomActionUrls(businessObject, pkNames);
	}

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		// TODO Auto-generated method stub
		return super.getSearchResults(fieldValues);
	}

}
