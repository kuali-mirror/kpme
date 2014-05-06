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

package org.kuali.kpme.tklm.time.rules.shiftdifferential.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.kpme.core.lookup.KPMELookupableHelperServiceImpl;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.kns.web.ui.Column;


@SuppressWarnings("deprecation")
public class ShiftDifferentialRuleLookupableHelperServiceImpl extends KPMELookupableHelperServiceImpl {

    @Override
    public List<Column> getColumns() {
        List<Column> columns = super.getColumns();
        for (Column column : columns) {
            if (column.getFormatter() instanceof org.kuali.kpme.core.SqlTimeFormatter)
            {
                column.setSortable(Boolean.FALSE.toString());
            }
        }
        return columns;
    }

	private static final long serialVersionUID = -7636153206208704542L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		ShiftDifferentialRule shiftDifferentialRule = (ShiftDifferentialRule) businessObject;
		String tkShiftDiffRuleId = shiftDifferentialRule.getTkShiftDiffRuleId();

		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("tkShiftDiffRuleId", tkShiftDiffRuleId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String location = fieldValues.get("location");
        String payGrade = fieldValues.get("payGrade");
        String hrSalGroup = fieldValues.get("hrSalGroup");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        return TkServiceLocator.getShiftDifferentialRuleService().getShiftDifferentialRules(GlobalVariables.getUserSession().getPrincipalId(), location, hrSalGroup, payGrade, TKUtils.formatDateString(fromEffdt),
                TKUtils.formatDateString(toEffdt), active, showHist);
    }

}
