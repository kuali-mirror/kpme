package org.kuali.hr.time.earncodegroup.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

public class EarnCodeGroupLookupableHelper extends HrEffectiveDateActiveLookupableHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			@SuppressWarnings("rawtypes") List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
		for(HtmlData actionUrl : customActionUrls){
			if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
				overrideUrls.add(actionUrl);
			}
		}
		if (TKContext.getUser().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			EarnCodeGroup earnGroupObj = (EarnCodeGroup) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final String earnGroup = earnGroupObj.getEarnCodeGroup();
			final String hrEarnGroupId = earnGroupObj.getHrEarnCodeGroupId();
			HtmlData htmlData = new HtmlData() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&hrEarnGroupId="
							+ hrEarnGroupId + "\">view</a>";
				}
			};
			overrideUrls.add(htmlData);
		} else if (overrideUrls.size() != 0) {
			overrideUrls.remove(0);
		}
		return overrideUrls;
	}
}
