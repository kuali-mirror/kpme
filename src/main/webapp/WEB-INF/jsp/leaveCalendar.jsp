<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${LeaveCalendarForm}" scope="request"/>

<tk:tkHeader tabId="leaveCalendar">
    <script src="js/underscore-1.3.1.min.js"></script>
    <%--<script src="js/underscore.string-2.0.0.js"></script>--%>
    <script src="js/backbone-0.9.1.min.js"></script>
    <script src="js/tk.ui.js"></script>
    <script src="js/tk.leaveCalendar.backbone.js"></script>
    <tk:calendar cal="${Form.leaveCalendar}" docId="${Form.documentId}" calType="leaveCalendar"/>

    <div id="cal">
 	<html:hidden property="documentId" value="${Form.documentId}" styleId="documentId"/>
    <html:hidden property="prevDocumentId" value="${Form.prevDocumentId}" styleId="prevDocumentId"/>
    <html:hidden property="nextDocumentId" value="${Form.nextDocumentId}" styleId="nextDocumentId"/>
    <html:hidden  value="${Form.leaveCalendar.beginDateTime}" styleId="beginPeriodDate"/>
           <div id="dialog-form" class="dialog-form" title="Add Ledgers:">
            <html:form action="/LeaveCalendar.do" styleId="leaveBlock-form">

                <html:hidden property="methodToCall" value="" styleId="methodToCall"/>
                <html:hidden property="beginPeriodDateTime" value="${Form.beginPeriodDateTime}" styleId="beginPeriodDateTime"/>
                <html:hidden property="endPeriodDateTime" value="${Form.endPeriodDateTime}" styleId="endPeriodDateTime"/>

                <div class="ui-widget timesheet-panel" id="timesheet-panel">
                    <table>
                        <tr>
                            <td><label for="date-range">* Date range:</label></td>
                            <td><input name="startDate" title="Date Begin" type="text" id="startDate" size="10"/>
                                -
                                <input name="endDate" title="Date End" type="text" id="endDate" size="10"/></td>
                        </tr>
                        <tr>
                            <td><label for="leave-code">* Leave Code:</label></td>
                            <td>
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
                                <html:textarea property="description" cols="21"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </html:form>
        </div>
    </div>
</tk:tkHeader>