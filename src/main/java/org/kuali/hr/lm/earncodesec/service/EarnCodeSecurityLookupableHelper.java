package org.kuali.hr.lm.earncodesec.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class EarnCodeSecurityLookupableHelper extends
		HrEffectiveDateActiveLookupableHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		if (TKContext.getUser().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			EarnCodeSecurity departmentEarnCode = (EarnCodeSecurity) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final String hrDeptEarnCodeId = departmentEarnCode
					.getHrEarnCodeSecurityId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&hrEarnCodeSecurityId="
							+ hrDeptEarnCodeId + "\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
        String salGroup = fieldValues.get("hrSalGroup");
        String dept = fieldValues.get("dept");
        String earnCode = fieldValues.get("earnCode");
        String location = fieldValues.get("location");
        String fromEffdt = fieldValues.get("rangeLowerBoundKeyPrefix_effectiveDate");
        String toEffdt = StringUtils.isNotBlank(fieldValues.get("effectiveDate")) ? fieldValues.get("effectiveDate").replace("<=", "") : "";
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");
        System.out.println("field value map is >>> "+fieldValues);
        System.out.println("actual list is  "+TkServiceLocator.getEarnCodeSecurityService().searchEarnCodeSecurities(dept, salGroup, earnCode, location, 
				TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist));
		return TkServiceLocator.getEarnCodeSecurityService().searchEarnCodeSecurities(dept, salGroup, earnCode, location, 
								TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist);
	}

}
