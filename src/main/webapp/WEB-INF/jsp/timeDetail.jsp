<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeDetailActionForm}" scope="request"/>

<tk:tkHeader tabId="timeDetail">
    <div style="clear:both;" class="">
        <html:hidden property="beginPeriodDateTime" value="${Form.beginPeriodDateTime}" styleId="beginPeriodDate"/>
        <html:hidden property="endPeriodDateTime" value="${Form.endPeriodDateTime}" styleId="endPeriodDate"/>
        <html:hidden property="isVirtualWorkDay" value="${Form.isVirtualWorkDay}" styleId="isVirtualWorkDay"/>
        <html:hidden property="serverTimezone" value="${Form.serverTimezone}" styleId="serverTimezone"/>
        <html:hidden property="userTimezone" value="${Form.userTimezone}" styleId="userTimezone"/>
        <html:hidden property="calNav" value="${Form.calNav}" styleId="calNav"/>
        <html:hidden property="documentId" value="${Form.documentId}" styleId="documentId"/>
        <html:hidden property="warningJson" value="${Form.warningJason}" styleId="warningJson"/>

            <%--This is for visually impaired users --%>
        <!--
        <c:forEach var="timeBlock" items="${Form.timeBlockList}" varStatus="row">
            Document Id: ${timeBlock.documentId}<br/>
            Job Number: ${timeBlock.jobNumber}<br/>
            Workarea Id: ${timeBlock.workArea}<br/>
            Task Id: ${timeBlock.task}<br/>
            Earn Code: ${timeBlock.earnCode}<br/>
            Begin Time: <fmt:formatDate type="both" dateStyle="full" value="${timeBlock.beginTimestamp}"/><br/>
            End Time: <fmt:formatDate type="both" dateStyle="full" value="${timeBlock.endTimestamp}"/><br/>
            Hours: ${timeBlock.hours}<br/>
            Amount: ${timeBlock.amount}<br/>
            <br/>
        </c:forEach>
         -->

        <%-- this is used by the javascript to fetch the time block json --%>
        <html:textarea property="timeBlockString" styleId="timeBlockString" value="${Form.timeBlockString}"/>

        <div class="global-error"><!-- Error: This is a global error for the demo purpose  --></div>
        <tk:calendar cal="${Form.tkCalendar}"/>

        <div id="cal" style="margin: 20px auto 20px auto; width:95%; font-size:.9em;">

            <div id="dialog-form" title="Add time blocks:" style="margin-left: auto; margin-right: auto;">
                <p id="validation" class="validation" title="Validation">All form fields are required.</p>
                <html:form action="/TimeDetail.do" styleId="time-detail">
                    <html:hidden property="methodToCall" value="" styleId="methodToCall"/>
                    <html:hidden property="tkTimeBlockId" value="" styleId="tkTimeBlockId"/>
                    <html:hidden property="startDate" styleId="startDate"/>
                    <html:hidden property="endDate" styleId="endDate"/>
                    <html:hidden property="startTime" styleId="startTime"/>
                    <html:hidden property="endTime" styleId="endTime"/>
                    <html:hidden property="hours" styleId="hours"/>
                    <html:hidden property="amount" styleId="amount"/>
                    <html:hidden property="selectedEarnCode" styleId="selectedEarnCode"/>
                    <html:hidden property="selectedAssignment" styleId="selectedAssignment"/>
                    <html:hidden property="acrossDays" styleId="acrossDays"/>

                    <div class="ui-widget" id="timesheet-panel">
                        <table>
                            <tr>
                                <td><label for="date-range-begin">Date range:</label></td>
                                <td><input title="Date Begin" type="text" id="date-range-begin" size="10"/> -
                                    <input title="Date End" type="text" id="date-range-end" size="10"/></td>
                            </tr>
                            <tr>
                                <td><label for="assignment" >Assignment:</label></td>
                                <td>
                                    <tk:assignment assignments="${Form.assignmentDescriptions}"/>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="earnCode" >Earn code:</label></td>
                                <td>
                                    <select id='earnCode' name="selectedEarnCode">
                                    </select>
                                </td>
                            </tr>
                            <tr id="clockIn">
                                <td><span style="float:right;"><label for="beginTimeField">In:</label></span></td>
                                <td>
                                    <input name="beginTimeField" id="beginTimeField" type="text" size="10"/>
                                    <button style="width:20px; height:20px; vertical-align: text-top"
                                            title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530"
                                            id="beginTimeHelp" tabindex="999">help
                                    </button>
                                    <div id="beginTimeField-error" style="color:red;"></div>
                                    <input type="hidden" id="beginTimeField-messages" title="Errors on BeginTime Field"/>
                                </td>
                            </tr>
                            <tr id="clockOut">
                                <td><span style="float:right;"><label for="endTimeField">Out:</label></span></td>
                                <td>
                                    <input name="endTimeField" id="endTimeField" type="text" size="10"/>
                                    <button style="width:20px; height:20px; vertical-align: text-top" id="endTimeHelp"
                                            title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530"
                                            tabindex="999">help
                                    </button>
                                    <div id="endTimeField-error" style="color:red;"></div>
                                    <input type="hidden" id="endTimeField-messages" title="Errors on EndTime Field"/>
                                </td>
                            </tr>
                            <tr id="hoursSection" style="display: none;">
                                <td><label for="hoursField">Hours:</label></td>
                                <td>
                                    <input id="hoursField" name="hours"/>
                                </td>
                            </tr>
                            <tr id="amountSection" style="display: none;">
                                <td><label for="amountField" >Amount:</label></td>
                                <td>
                                    $ <input id="amountField" name="amount"/>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="checkbox" id="acrossDaysField" value="n" title="Check time if you want to cross days"/><label for="acrossDaysField">Apply time to each day</label></td>
                            </tr>
                        </table>
                    </div>
                </html:form>
            </div>
                <%-- end of dialog-form --%>

            <tk:tkTimesheetRouting/>

                <%--<tk:timeSummary timeSummary="${Form.timesheetDocument.timeSummary}" />  --%>
            <tk:timeSummary timeSummary="${Form.timeSummary}"/>
        </div>
    </div>
</tk:tkHeader>
<tk:routeLog/>
<tk:note/>
