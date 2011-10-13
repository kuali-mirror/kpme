package org.kuali.hr.lm.leaveplan.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

/**
 * Used to override lookup functionality for the leave plan lookup
 * 
 * 
 */
public class LeavePlanLookupableHelper extends
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
			List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
			for(HtmlData actionUrl : customActionUrls){
				if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
					overrideUrls.add(actionUrl);
				}
			}

		if (TKContext.getUser().getCurrentRoles().isSystemAdmin()) {
			LeavePlan leavePlan = (LeavePlan) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long lmLeavePlanId = leavePlan
					.getLmLeavePlanId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&lmLeavePlanId="
							+ lmLeavePlanId + "\">view</a>";
				}
			};
			overrideUrls.add(htmlData);
		} else if (overrideUrls.size() != 0) {
			overrideUrls.remove(0);
		}
		return overrideUrls;
	}

}
