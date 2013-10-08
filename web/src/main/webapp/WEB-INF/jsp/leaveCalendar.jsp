<%--

    Copyright 2004-2013 The Kuali Foundation

    Licensed under the Educational Community License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.opensource.org/licenses/ecl2.php

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${LeaveCalendarForm}" scope="request"/>

<tk:tkHeader tabId="leaveCalendar" nocache="true">
    <html:form action="/LeaveCalendar.do" method="POST">
        <html:hidden property="reloadValue" value="" styleId="reloadValue"/>
        <html:hidden property="documentId" value="${Form.documentId}" styleId="documentId"/>
        <html:hidden property="prevDocumentId" value="${Form.prevDocumentId}" styleId="prevDocumentId"/>
        <html:hidden property="nextDocumentId" value="${Form.nextDocumentId}" styleId="nextDocumentId"/>
        <html:hidden property="hrCalendarEntryId" value="${Form.hrCalendarEntryId}" styleId="hrCalendarEntryId"/>
        <html:hidden property="prevHrCalendarEntryId" value="${Form.prevHrCalendarEntryId}" styleId="prevHrCalendarEntryId"/>
        <html:hidden property="nextHrCalendarEntryId" value="${Form.nextHrCalendarEntryId}" styleId="nextHrCalendarEntryId"/>
        <html:hidden property="docEditable" value="${Form.docEditable}" styleId="docEditable"/>
        <html:hidden property="leaveCalendar.beginDateTime" value="${Form.leaveCalendar.beginDateTime}" styleId="beginPeriodDate"/>
        <html:hidden property="blockSubmittable" value="${Form.blockSubmittable}" styleId="blockSubmittable"/>
            
        <script src="${ConfigProperties.js.dir}/underscore-1.3.1.min.js"></script>
        <script src="${ConfigProperties.js.dir}/backbone-0.9.1.min.js"></script>
        <script src="${ConfigProperties.js.dir}/tk.ui.js"></script>
        <script src="${ConfigProperties.js.dir}/common.calendar.backbone.js"></script>
        <script src="${ConfigProperties.js.dir}/tk.leaveCalendar.backbone.js"></script>
        <script type="text/javascript">
            jQuery(document).ready(function()
            {
                var d = new Date();
                d = d.getTime();
                var reloadVal = $('#reloadValue');
                if (reloadVal.val().length == 0) {
                    reloadVal.val(d);
                    $('body').show();
                } else {
                    reloadVal.val('');
                    location.reload();
                }
            });
        </script>
    
        <c:if test="${not empty Form.calendarEntry}">
        
            <tk:calendar cal="${Form.leaveCalendar}" docId="${Form.documentId}" calType="leaveCalendar"/>
            
            <%-- if this leave calendar does not have a leave calendar document, then do not show routing sections --%>
            <c:if test="${not empty Form.documentId}">
            	<%-- render the calendar buttons --%>
           		<lm:lmLeaveCalendarRouting/>
            </c:if>
            
            <lm:leaveSummary leaveSummary="${Form.leaveSummary}" />
                    
            <c:if test="${not empty Form.documentId}">
            	<%-- route logs --%>
        		<lm:routeLog/>
                <%-- notes --%>
            	<lm:note/>
            </c:if>
            
            <%-- this is used by the javascript to fetch the leave block json --%>
            <html:textarea property="leaveBlockString" styleId="leaveBlockString" value="${Form.leaveBlockString}"/>
        </c:if>
    </html:form>
</tk:tkHeader>

<%-- The leave entry form (dialog) --%>
<div id="cal">
    <div id="dialog-form" class="dialog-form" title="Add Ledgers:">
        <html:form action="/LeaveCalendar.do" styleId="leaveBlock-form">
            <p id="validation" class="validation" title="Validation">All form fields are .</p>

            <html:hidden property="methodToCall" value="" styleId="methodToCall"/>
            <html:hidden property="documentId" value="${Form.documentId}" styleId="documentId"/>
            <html:hidden property="hrCalendarEntryId" value="${Form.hrCalendarEntryId}" styleId="hrCalendarEntryId"/>
            <html:hidden property="beginPeriodDateTime" value="${Form.beginPeriodDateTime}" styleId="beginPeriodDateTime"/>
            <html:hidden property="endPeriodDateTime" value="${Form.endPeriodDateTime}" styleId="endPeriodDateTime"/>
            <html:hidden property="leaveBlockId" value="" styleId="leaveBlockId"/>

            <div class="ui-widget timesheet-panel" id="timesheet-panel">
                <table>
                    <tr>
                        <td><label for="date-range">* Date range:</label></td>
                        <td><input name="startDate" title="Date Begin" type="text" id="startDate" size="10"/>
                            -
                            <input name="endDate" title="Date End" type="text" id="endDate" size="10"/></td>
                    </tr>
                     <tr>
                        <td><label for="selectedAssignment">Assignment:</label></td>
                        <td>
                            <tk:assignment assignments="${Form.assignmentDescriptions}"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="leave-code">* Earn Code:</label></td>
                        <td>
                            <div id="earnCode-section">
                                <select id='selectedEarnCode' name="selectedEarnCode">
                                    <option value=''>-- select an earn code --</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr class="leaveAmountSection">
                        <td><label for="leave-amount" id="unitOfTime">* Leave Amount</label> : </label></td>
                        <td>
                            <html:text property="leaveAmount" styleId="leaveAmount"/>
                        </td>
                    </tr>
                    <tr class="clockInSection" style="display: none;">
                        <td><span style="float:right;"><label for="startTimeHourMinute">In:</label></span></td>
                        <td>
                            <input name="startTimeHourMinute" id="startTimeHourMinute" type="text" size="10"/>

                                <%-- Time entry helper with the tooltip effect --%>
                            <button style="width:20px; height:20px; vertical-align: text-top"
                                    title="Supported formats:<br/>9a, 9 am, 9:00a, 9:00 am, 3p, 3 pm, 3:00p, 3:00 pm, 900, 15:00"
                                    class="beginTimeHelp" tabindex="999" onclick="return false;">help
                            </button>

                            <input type="hidden" name="startTime" id="startTime"/>
                        </td>
                    </tr>
                    <tr class="clockOutSection" style="display: none;">
                        <td><span style="float:right;"><label for="endTimeHourMinute">Out:</label></span></td>
                        <td>
                            <input name="endTimeHourMinute" id="endTimeHourMinute" type="text" size="10"/>

                                <%-- Time entry helper with the tooltip effect --%>
                            <button style="width:20px; height:20px; vertical-align: text-top;"
                                    title="Supported formats:<br/>9a, 9 am, 9:00a, 9:00 am, 3p, 3 pm, 3:00p, 3:00 pm, 900, 15:00"
                                    class="endTimeHelp" tabindex="999" onclick="return false;">help
                            </button>

                            <input type="hidden" name="endTime" id="endTime"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="description">Description:</label></td>
                        <td>
                            <html:textarea property="description" styleId="description" cols="21"/>
                        </td>
                    </tr>
                    <!--  KPME-1446  -->
                    <tr>
                        <td></td>
                        <td>
                            <input type="checkbox" name="spanningWeeks" id="spanningWeeks" value="n" title="Check if you want to span weeks over weekend"/>
                            <label for="spanningWeeks">Include weekends</label>
                        </td>
                    </tr>
                    <c:if test="${empty Form.documentId && Form.blockSubmittable}">  <!-- KPME-2540  -->
	                    <tr> 
	                        <td></td>
	                        <td>
	                            <input type="checkbox" name="approval" id="approval" value="n" title="Check if you want to submit leave request for approval"/>
	                            <label for="approval">Submit leave request for approval</label>
	                        </td>
	                    </tr>
                    </c:if>
                </table>
            </div>
        </html:form>
    </div>
</div>

<%-- Earn code template --%>
<script type="text/template" id="earnCode-template">
    <option value="<@= earnCode @>"><@= earnCode + " : " + desc @></option>
</script>

<div id="lm-transfer-dialog" title="Balance Transfer" style="display:none;">

</div>

<div id="lm-payout-dialog" title="Leave Payout" style="display:none;">

</div>

<div id="confirm-forfeiture-dialog" class="dialog-form" title="Confirm Forfeiture">
   <html:form action="/LeaveCalendarSubmit.do" styleId="forfeiture-form">
		<html:hidden property="loseOnSubmit" value="${not empty Form.forfeitures}" styleId="loseOnSubmit"/>
		 
        <div class="ui-widget timesheet-panel" id="timesheet-panel-forfeit">
            <table>
				<tr>
					<th></th>
					<th>Amount</th>
				</tr>
				<tbody>
				<c:forEach var="forfeiture" items="${Form.forfeitures}">
					<tr>
						<td>${forfeiture.fromAccrualCategory}: </td>
						<td align="right">${forfeiture.forfeitedAmount}</td>
					</tr>
				</c:forEach>
				</tbody>
            </table>
        </div>
    </html:form>
</div>
