package org.kuali.hr.time.position.service;

import java.util.List;

import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;

public class PositionLookupableHelper extends HrEffectiveDateActiveLookupableHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		Position position = (Position) businessObject;
		final Long hrPositionId = position.getHrPositionId();
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
							+ "&methodToCall=start&hrPositionId="
							+ hrPositionId
							+ "&positionNumber=\">view</a>";
				}
			};
			customActionUrls.add(htmlData);
		} else if (customActionUrls.size() != 0) {
			customActionUrls.remove(0);
		}
		return customActionUrls;
	}

}
