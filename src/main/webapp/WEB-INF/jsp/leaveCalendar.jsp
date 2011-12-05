<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${LeaveCalendarForm}" scope="request"/>


<tk:tkHeader tabId="leaveCalendar">
    <tk:calendar cal="${Form.leaveCalendar}" docId="${Form.documentId}" calType="leaveCalendar"/>

    <div id="cal" style="margin: 20px auto 20px auto; width:95%; font-size:.9em;">
        <div id="dialog-form" title="Add Ledgers:" style="margin-left: auto; margin-right: auto; display:none;">
            <html:form action="/LeaveCalendar.do?methodToCall=addLedger" styleId="ledger-entry-form">
                <div class="ui-widget" id="timesheet-panel">
                    <table>
                        <tr>
                            <td><label for="date-range">* Date range:</label></td>
                            <td><input name="beginDate" title="Date Begin" type="text" id="date-range-begin" size="10"/>
                                -
                                <input name="endDate" title="Date End" type="text" id="date-range-end" size="10"/></td>
                        </tr>
                        <tr>
                            <td><label for="leave-code">* Leave Code:</label></td>
                            <td>
                                    <%--<html:text property="selectedLeaveCode"/>--%>
                                <html:select property="selectedLeaveCode">
                                    <c:forEach var="leaveCode" items="${Form.leaveCalendar.leaveCodeList}">
                                        <option value="${leaveCode.key}">${leaveCode.value}</option>
                                    </c:forEach>
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="hours">* Hours:</label></td>
                            <td>
                                <html:text property="hours"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="description">Description:</label></td>
                            <td>
                                <html:textarea property="description"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </html:form>
        </div>
    </div>
</tk:tkHeader>