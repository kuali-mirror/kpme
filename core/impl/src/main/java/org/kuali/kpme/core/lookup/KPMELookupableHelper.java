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
package org.kuali.kpme.core.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.location.dao.LocationDao;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class KPMELookupableHelper extends KualiLookupableHelperServiceImpl {

	private static final long serialVersionUID = 6428435554717901643L;

    @Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = new ArrayList<HtmlData>();
        List<HtmlData> existingCustomActionUrls = super.getCustomActionUrls(businessObject, pkNames);
        HrBusinessObject bo = (HrBusinessObject) businessObject;
        if (!bo.isActive()) {
            if (!HrContext.canEditInactiveRecords()) {    //KPME-2699
                for (HtmlData action : customActionUrls) {
                    if (StringUtils.equals(action.getMethodToCall(),"edit")) {
                        customActionUrls.remove(action);
                        break;
                    }
                }
            }
        }
		for (HtmlData existingCustomActionUrl : existingCustomActionUrls) {
			if (!StringUtils.equals(existingCustomActionUrl.getMethodToCall(), KRADConstants.MAINTENANCE_COPY_METHOD_TO_CALL)) {
				customActionUrls.add(existingCustomActionUrl);
			}
		}
		
		return customActionUrls;
	}

}