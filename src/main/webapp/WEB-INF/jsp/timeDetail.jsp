<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeDetailActionForm}" scope="request"/>


<tk:tkHeader tabId="timeDetail">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.2.2/underscore-min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.string/1.1.6/underscore.string.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/0.5.3/backbone-min.js"></script>
    <script src="js/tk.calendar.backbone.js"></script>
    <script src="js/tk.ui.js"></script>

    <div style="clear:both;" class="">
        <html:hidden property="beginPeriodDateTime" value="${Form.beginPeriodDTNoTZ}" styleId="beginPeriodDate"/>
        <html:hidden property="endPeriodDateTime" value="${Form.endPeriodDTNoTZ}" styleId="endPeriodDate"/>
        <html:hidden property="isVirtualWorkDay" value="${Form.isVirtualWorkDay}" styleId="isVirtualWorkDay"/>
        <html:hidden property="serverTimezone" value="${Form.serverTimezone}" styleId="serverTimezone"/>
        <html:hidden property="userTimezone" value="${Form.userTimezone}" styleId="userTimezone"/>
        <html:hidden property="calNav" value="${Form.calNav}" styleId="calNav"/>
        <html:hidden property="documentId" value="${Form.documentId}" styleId="documentId"/>
        <html:hidden property="prevDocumentId" value="${Form.prevDocumentId}" styleId="prevDocumentId"/>
        <html:hidden property="nextDocumentId" value="${Form.nextDocumentId}" styleId="nextDocumentId"/>
        <html:hidden property="warningJson" value="${Form.warnings}" styleId="warningJson"/>
        <html:hidden property="docEditable" value="${Form.docEditable}" styleId="docEditable"/>

            <%--This is for visually impaired users --%>
        <!--
        <c:forEach var="timeBlock" items="${Form.timeBlockList}" varStatus="row">
            Document Id: ${timeBlock.documentId}<br/>
            Job Number: ${timeBlock.jobNumber}<br/>
            Workarea Id: ${timeBlock.workArea}<br/>
            Task Id: ${timeBlock.task}<br/>
            Earn Code: ${timeBlock.earnCode}<br/>
            Begin Time: <fmt:formatDate type="both" dateStyle="full" value="${timeBlock.beginTimeDisplayDate}"/><br/>
            End Time: <fmt:formatDate type="both" dateStyle="full" value="${timeBlock.endTimeDisplayDate}"/><br/>
            Hours: ${timeBlock.hours}<br/>
            Amount: ${timeBlock.amount}<br/>
            <br/>
        </c:forEach>
         -->

            <%-- this is used by the javascript to fetch the time block json --%>
        <html:textarea property="timeBlockString" styleId="timeBlockString" value="${Form.timeBlockString}"/>

            <%-- render the calendar --%>
        <tk:calendar cal="${Form.tkCalendar}" docId="${Form.documentId}"/>

            <%-- render the calendar buttons --%>
        <tk:tkTimesheetRouting/>

            <%-- render time summary --%>
        <tk:timeSummary timeSummary="${Form.timeSummary}"/>

            <%-- route logs --%>
        <tk:routeLog/>

            <%-- notes --%>
        <tk:note/>
    </div>

    <%-- The time entry form (dialog) --%>
    <div id="cal" style="margin: 20px auto 20px auto; width:95%; font-size:.9em; display: none;">
        <div id="dialog-form" title="Add Time Blocks:" class="dialog-form" style="margin-left: auto; margin-right: auto;">
            <p id="validation" class="validation" title="Validation">All form fields are required.</p>
            <html:form action="/TimeDetail.do" styleId="time-detail">
                <html:hidden property="methodToCall" value="" styleId="methodToCall"/>
                <html:hidden property="documentId" value="${Form.documentId}" styleId="documentId"/>
                <html:hidden property="tkTimeBlockId" value="" styleId="tkTimeBlockId"/>

                <div class="ui-widget timesheet-panel" id="timesheet-panel">
                    <table>
                        <tr>
                            <td><label for="date-range-begin">Date range:</label></td>
                            <td><input title="Date Begin" type="text" id="startDate" name="startDate" size="10"/> -
                                <input title="Date End" type="text" id="endDate" name="endDate" size="10"/></td>
                        </tr>
                        <tr>
                            <td><label for="selectedAssignment">Assignment:</label></td>
                            <td>
                                <tk:assignment assignments="${Form.assignmentDescriptions}"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="selectedEarnCode">Earn code:</label></td>
                            <td>
                                <div id="earnCode-section">
                                    <select id='selectedEarnCode' name="selectedEarnCode">
                                        <option value=''>-- select an earn code --</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr class="clockInSection">
                            <td><span style="float:right;"><label for="beginTimeField">In:</label></span></td>
                            <td>
                                <input name="startTime" id="startTime" type="text" size="10"/>

                                    <%-- Time entry helper with the tooltip effect --%>
                                <button style="width:20px; height:20px; vertical-align: text-top"
                                        title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 15:30, 1530"
                                        id="beginTimeHelp" tabindex="999" onclick="return false;">help
                                </button>

                                <input type="text" name="startTimeHourMinute" id="startTimeHourMinute"/>
                            </td>
                        </tr>
                        <tr class="clockOutSection">
                            <td><span style="float:right;"><label for="endTimeField">Out:</label></span></td>
                            <td>
                                <input name="endTime" id="endTime" type="text" size="10"/>

                                    <%-- Time entry helper with the tooltip effect --%>
                                <button style="width:20px; height:20px; vertical-align: text-top;"
                                        id="startTimeHelp"
                                        title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 15:30, 1530"
                                        tabindex="999" onclick="return false;">help
                                </button>

                                <input type="text" name="endTimeHourMinute" id="endTimeHourMinute"/>
                            </td>
                        </tr>
                        <tr class="hourSection" style="display: none;">
                            <td><label for="hoursField">Hours:</label></td>
                            <td>
                                <input id="hours" name="hours"/>
                            </td>
                        </tr>
                        <tr class="amountSection" style="display: none;">
                            <td><label for="amountField">Amount:</label></td>
                            <td>
                                $ <input id="amount" name="amount"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="checkbox" id="acrossDays" value="n"
                                       title="Check time if you want to cross days"/><label for="acrossDays">Apply
                                time to each day</label></td>
                        </tr>
                    </table>
                </div>
                <div id="overtime-section" style="display:none;" title="Change Overtime Earn Code:" class="timesheet-panel dialog-form">
                    <select id='overtimePref' name="overtimePref">
                    </select>
                </div>

            </html:form>
        </div>
    </div>


    <%-- Earn code template --%>
    <script type="text/template" id="earnCode-template">
        <option value="<@= earnCode @>"><@= earnCode + " : " + desc @></option>
    </script>

    <%-- Overtime template --%>
    <script type="text/template" id="overtime-template">
        <option value="<@= earnCode @>"><@= earnCode + " : " + desc @></option>
    </script>

</tk:tkHeader>
