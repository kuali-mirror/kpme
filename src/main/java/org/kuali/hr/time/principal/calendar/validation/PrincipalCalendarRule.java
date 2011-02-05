package org.kuali.hr.time.principal.calendar.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PrincipalCalendarRule extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomSaveDocumentBusinessRules(
			MaintenanceDocument document) {
		
		PrincipalCalendar principalCal = (PrincipalCalendar)this.getNewBo();
		PayCalendar payCal = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(principalCal.getCalendarGroup());
		if(payCal == null){
			this.putFieldError("calendarGroup", "principal.cal.pay.invalid",principalCal.getCalendarGroup());
			return false;
		}
		
		//holiday calendar is not required
		if(StringUtils.isNotEmpty(principalCal.getHolidayCalendarGroup())){
			HolidayCalendar holidayCal = TkServiceLocator.getHolidayCalendarService().getHolidayCalendarByGroup(principalCal.getHolidayCalendarGroup());
			if(holidayCal == null){
				this.putFieldError("holidayCalendarGroup", "principal.cal.holiday.invalid", principalCal.getHolidayCalendarGroup());
				return false;
			}
		}
		
		return true;
	}

}
