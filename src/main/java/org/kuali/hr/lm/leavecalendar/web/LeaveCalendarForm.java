package org.kuali.hr.lm.leavecalendar.web;

import org.apache.struts.action.ActionMapping;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.LeaveCalendar;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LeaveCalendarForm extends TkForm {

    private static final DateTimeFormatter SDF_NO_TZ = DateTimeFormat.forPattern("EEE MMM d HH:mm:ss yyyy");

    private String documentId;
    private LeaveCalendar leaveCalendar;
    private LeaveCalendarDocument leaveCalendarDocument;
    private List<String> warnings;
    CalendarEntries calendarEntry;
    private String startDate;
    private String endDate;
    private String selectedLeaveCode;
    private Map<String, String> leaveCodeList = new LinkedHashMap<String, String>();
    private BigDecimal leaveAmount;
    private String description;
    private String leaveBlockId;
    private String prevDocumentId;
    private String nextDocumentId;
    private String prevCalEntryId;
    private String nextCalEntryId;
    private String calEntryId;

    public String getPrevCalEntryId() {
		return prevCalEntryId;
	}

	public void setPrevCalEntryId(String prevCalEntryId) {
		this.prevCalEntryId = prevCalEntryId;
	}

	public String getNextCalEntryId() {
		return nextCalEntryId;
	}

	public void setNextCalEntryId(String nextCalEntryId) {
		this.nextCalEntryId = nextCalEntryId;
	}

	public String getCalEntryId() {
		return calEntryId;
	}

	public void setCalEntryId(String calEntryId) {
		this.calEntryId = calEntryId;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    public LeaveCalendar getLeaveCalendar() {
        return leaveCalendar;
    }

    public void setLeaveCalendar(LeaveCalendar leaveCalendar) {
        this.leaveCalendar = leaveCalendar;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public CalendarEntries getCalendarEntry() {
        return calendarEntry;
    }

    public void setCalendarEntry(CalendarEntries calendarEntry) {
        this.calendarEntry = calendarEntry;
    }

    public LeaveCalendarDocument getLeaveCalendarDocument() {
        return leaveCalendarDocument;
    }

    public void setLeaveCalendarDocument(LeaveCalendarDocument leaveCalendarDocument) {
        this.leaveCalendarDocument = leaveCalendarDocument;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSelectedLeaveCode() {
        return selectedLeaveCode;
    }

    public void setSelectedLeaveCode(String selectedLeaveCode) {
        this.selectedLeaveCode = selectedLeaveCode;
    }

	public BigDecimal getLeaveAmount() {
		return leaveAmount;
	}

	public void setLeaveAmount(BigDecimal leaveAmount) {
		this.leaveAmount = leaveAmount;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getLeaveCodeList() {
        return leaveCodeList;
    }

    public void setLeaveCodeList(Map<String, String> leaveCodeList) {
        this.leaveCodeList = leaveCodeList;
    }

    public String getLeaveBlockId() {
        return leaveBlockId;
    }

    public void setLeaveBlockId(String leaveBlockId) {
        this.leaveBlockId = leaveBlockId;
    }

    public String getNextDocumentId() {
        return nextDocumentId;
    }

    public void setNextDocumentId(String nextDocumentId) {
        this.nextDocumentId = nextDocumentId;
    }

    public String getPrevDocumentId() {
        return prevDocumentId;
    }

    public void setPrevDocumentId(String prevDocumentId) {
        this.prevDocumentId = prevDocumentId;
    }

    public String getBeginPeriodDateTime() {
        return leaveCalendar.getBeginDateTime().toString(SDF_NO_TZ);
    }

    public String getEndPeriodDateTime() {
        return leaveCalendar.getEndDateTime().toString(SDF_NO_TZ);
    }
}
