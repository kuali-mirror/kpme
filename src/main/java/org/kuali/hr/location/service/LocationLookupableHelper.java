package org.kuali.hr.location.service;

import java.util.List;

import org.kuali.hr.location.Location;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class LocationLookupableHelper extends KualiLookupableHelperServiceImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		Location location = (Location) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final Long hrLocationId = location.getHrLocationId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&hrLocationId=" + hrLocationId
						+ "\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
}
