package org.kuali.hr.time.department.service;

import org.kuali.hr.time.department.Department;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

import java.util.List;

public class DepartmentLookupableHelper extends
		KualiLookupableHelperServiceImpl {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		Department department = (Department) businessObject;
		final String dept = department.getDept();
		final String className = this.getBusinessObjectClass().getName();
		List<HtmlData> customActionUrls = super
				.getCustomActionUrls(businessObject, pkNames);
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className
						+ "&methodToCall=start&dept="
						+ dept
						+ "&tkDeptId=\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}


}
