package org.kuali.hr.lm.leavecalendar.web;

import org.apache.struts.action.ActionMapping;
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

    private String documentId;
    private LeaveCalendar leaveCalendar;
    private LeaveCalendarDocument leaveCalendarDocument;
    private List<String> warnings;
    CalendarEntries calendarEntry;
    private String beginDate;
    private String endDate;
    private String selectedLeaveCode;
    private Map<String, String> leaveCodeLsit = new LinkedHashMap<String, String>();
    private BigDecimal hours;
    private String description;
    private String ledgerId;

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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
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

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getLeaveCodeLsit() {
        return leaveCodeLsit;
    }

    public void setLeaveCodeLsit(Map<String, String> leaveCodeLsit) {
        this.leaveCodeLsit = leaveCodeLsit;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }
}
