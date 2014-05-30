package org.kuali.kpme.tklm.time.clocklog.web;

import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.inquiry.InquirableImpl;

import java.util.Map;

public class ClockLogKradInquirableImpl extends InquirableImpl {


	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
    public ClockLogBo retrieveDataObject(Map fieldValues) {
		ClockLogBo aClockLog = (ClockLogBo) super.retrieveDataObject(fieldValues);
		if(aClockLog != null) {
			aClockLog.setClockedByMissedPunch(TkServiceLocator.getClockLogService().isClockLogCreatedByMissedPunch(aClockLog.getTkClockLogId()));
		}
        return aClockLog;
    }

}
