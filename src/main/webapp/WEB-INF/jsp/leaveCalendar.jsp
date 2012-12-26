<%--

    Copyright 2004-2012 The Kuali Foundation

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
    <script src="js/underscore-1.3.1.min.js"></script>
    <%--<script src="js/underscore.string-2.0.0.js"></script>--%>
    <script src="js/backbone-0.9.1.min.js"></script>
    <script src="js/tk.ui.js"></script>
    <script src="js/common.calendar.backbone.js"></script>
    <script src="js/tk.leaveCalendar.backbone.js"></script>
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

    <div id="cal">
    <html:hidden property="reloadValue" value="" styleId="reloadValue"/>
 	<html:hidden property="documentId" value="${Form.documentId}" styleId="documentId"/>
    <html:hidden property="prevDocumentId" value="${Form.prevDocumentId}" styleId="prevDocumentId"/>
    <html:hidden property="nextDocumentId" value="${Form.nextDocumentId}" styleId="nextDocumentId"/>
    <html:hidden property="prevCalEntryId" value="${Form.prevCalEntryId}" styleId="prevCalEntryId"/>
    <html:hidden property="nextCalEntryId" value="${Form.nextCalEntryId}" styleId="nextCalEntryId"/>
    <html:hidden property="docEditable" value="${Form.docEditable}" styleId="docEditable"/>
    <html:hidden property="currentPayCalStartDate" value="${Form.currentPayCalStartDate}" styleId="currentPayCalStartDate"/>
    <html:hidden property="currentPayCalEndDate" value="${Form.currentPayCalEndDate}" styleId="currentPayCalEndDate"/>
    
    <html:hidden property="leaveCalendar.beginDateTime" value="${Form.leaveCalendar.beginDateTime}" styleId="beginPeriodDate"/>
           <div id="dialog-form" class="dialog-form" title="Add Ledgers:">
            <html:form action="/LeaveCalendar.do" styleId="leaveBlock-form">

				<p id="validation" class="validation" title="Validation">All form fields are .</p>
				
                <html:hidden property="methodToCall" value="" styleId="methodToCall"/>
                <html:hidden property="beginPeriodDateTime" value="${Form.beginPeriodDateTime}" styleId="beginPeriodDateTime"/>
                <html:hidden property="endPeriodDateTime" value="${Form.endPeriodDateTime}" styleId="endPeriodDateTime"/>
                <html:hidden property="calEntryId" value="${Form.calEntryId}" styleId="calEntryId"/>
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
                        <tr>
                            <td><label for="leave-amount" id="unitOfTime">* Leave Amount</label> : </label></td>
                            <td>
                                <html:text property="leaveAmount" styleId="leaveAmount"/>
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
	
</tk:tkHeader>