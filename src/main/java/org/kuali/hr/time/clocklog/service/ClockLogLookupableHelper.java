package org.kuali.hr.time.clocklog.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class ClockLogLookupableHelper extends KualiLookupableHelperServiceImpl {
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
		
		//Add missed punch to the clock log if it has one
		ClockLog clockLog = (ClockLog)businessObject;
		MissedPunchDocument mpDoc = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(clockLog.getTkClockLogId());
		if(mpDoc != null){
			clockLog.setMissedPunchDocumentId(mpDoc.getDocumentNumber());
		}
		
		for(HtmlData actionUrl : customActionUrls){
			if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
				overrideUrls.add(actionUrl);
			}
		}
		if (TKContext.getUser().getCurrentRoles().isSystemAdmin()) {
			clockLog = (ClockLog) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long tkClockLogId = clockLog.getTkClockLogId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&tkClockLogId="
							+ tkClockLogId + "\">view</a>";
				}
			};
			overrideUrls.add(htmlData);
		} else if (overrideUrls.size() != 0) {
			overrideUrls.remove(0);
		}
		return overrideUrls;
	}
}
