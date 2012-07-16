<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${CalendarEntryActionForm}" scope="request"/>
<c:set var="KualiForm" value="${CalendarEntryActionForm}" scope="request"/>

<tk:tkHeader tabId="calendarEntry">

    <div id="calendar-content">
        <html:form action="/CalendarEntry.do" styleId="calendar-job">
            ${Form.message}
            <fieldset id="calendar-create">
                <legend>Create Calendar Entry</legend>
                <table>
                    <tr>
                        <th><label>No of Periods :</label></th>
                        <td>
                            <input type="text" name="noOfPeriods" id="noOfPeriods" value="${Form.noOfPeriods}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><label>Pay calendar period : </label></th>
                        <td>
                            <input type="text" name="hrPyCalendarEntryId" id="hrPyCalendarEntryId" value="${Form.hrPyCalendarEntryId}"/>
                        </td>
                        <td>
                        	<kul:lookup boClassName="org.kuali.hr.time.calendar.CalendarEntries"
                					fieldConversions="hrCalendarEntriesId:hrPyCalendarEntryId"
                					lookupParameters="" />
                        </td>
                    </tr>
                    <tr>
                        <th><label>Calendar frequency : </label></th>
                        <td>
                            <select name="calendarEntryPeriodType" id="calendarEntryPeriodType">
                                <option value="W">Weekly</option>
                                <option value="B">Biweekly</option>
                                <option value="S">Semi Monthly</option>
                                <option value="M">Monthly</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    	<td></td>
                    	<td>
                        	<html:image property="methodToCall.createCalendarEntry" src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif" alt="Submit" title="Submit" styleClass="globalbuttons" />
                        </td>
                    </tr>
                </table>
                <br/>
            </fieldset>
        </html:form>
    </div>


</tk:tkHeader>