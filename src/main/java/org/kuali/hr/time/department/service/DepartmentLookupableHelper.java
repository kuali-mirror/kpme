package org.kuali.hr.time.department.service;

import java.util.List;

import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

public class DepartmentLookupableHelper extends
		HrEffectiveDateActiveLookupableHelper {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		Department department = (Department) businessObject;
		final String dept = department.getDept();
		final String hrDeptId = department.getHrDeptId();
		final String className = this.getBusinessObjectClass().getName();
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		if (TKContext.getUser().isSystemAdmin() ||
				TKContext.getUser().isLocationAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			HtmlData htmlData = new HtmlData() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 3489101791881384392L;

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start"
							+ "&hrDeptId="
							+ hrDeptId
							+ "\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}

}
