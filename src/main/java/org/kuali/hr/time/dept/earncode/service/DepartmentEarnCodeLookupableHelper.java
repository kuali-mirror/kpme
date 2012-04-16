package org.kuali.hr.time.dept.earncode.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

import java.util.List;
import java.util.Map;

public class DepartmentEarnCodeLookupableHelper extends
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
		if (TKContext.getUser().getCurrentRoles().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			DepartmentEarnCode departmentEarnCode = (DepartmentEarnCode) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final String hrDeptEarnCodeId = departmentEarnCode
					.getHrDeptEarnCodeId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&hrDeptEarnCodeId="
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
		return TkServiceLocator.getDepartmentEarnCodeService().searchDepartmentEarnCodes(dept, salGroup, earnCode, location, 
								TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist);
	}

}
