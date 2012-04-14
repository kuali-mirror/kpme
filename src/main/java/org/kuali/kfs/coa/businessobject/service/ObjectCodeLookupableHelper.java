package org.kuali.kfs.coa.businessobject.service;

import java.util.List;

import org.kuali.hr.time.util.TKContext;
import org.kuali.kfs.coa.businessobject.ObjectCode;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class ObjectCodeLookupableHelper extends
		KualiLookupableHelperServiceImpl {
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
			ObjectCode objectCode = (ObjectCode) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final String financialObjectCode = objectCode
					.getFinancialObjectCode();
			final String chartOfAccountsCode = objectCode
					.getChartOfAccountsCode();
			final Integer universityFiscalYear = objectCode
					.getUniversityFiscalYear();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&financialObjectCode="
							+ financialObjectCode
							+ "&chartOfAccountsCode="
							+ chartOfAccountsCode
							+ "&universityFiscalYear="
							+ universityFiscalYear + "\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}
}
