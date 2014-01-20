<%--

    Copyright 2004-2014 The Kuali Foundation

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

<c:set var="Form" value="${LeaveRequestApprovalActionForm}" scope="request"/>
<c:set var="KualiForm" value="${LeaveRequestApprovalActionForm}" scope="request"/>

<tk:tkHeader tabId="leaveRequestApproval">
   <html:form action="/LeaveRequestApproval.do" styleId="leaveRequestApproval">
   <html:hidden property="reloadValue" value="" styleId="reloadValue"/>
   <html:hidden property="hrCalendarEntryId" value="${Form.hrCalendarEntryId}" styleId="hrCalendarEntryId"/>
   <html:hidden property="prevHrCalendarEntryId" value="${Form.prevHrCalendarEntryId}" styleId="prevHrCalendarEntryId"/>
   <html:hidden property="nextHrCalendarEntryId" value="${Form.nextHrCalendarEntryId}" styleId="nextHrCalendarEntryId"/>
   <html:hidden property="methodToCall" value="" styleId="methodToCall"/>
   <html:hidden property="actionList" value="" styleId="actionList"/>
   <html:hidden property="action" value="" styleId="action"/>
   <html:hidden property="beginDateString" value="${Form.beginDateString}" styleId="beginDateString"/>
   <html:hidden property="endDateString" value="${Form.endDateString}" styleId="endDateString"/>
   <html:hidden property="navigationAction" value="" styleId="navigationAction"/>
   
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

    <script src="${ConfigProperties.js.dir}/underscore-1.3.1.min.js"></script>
    <script src="${ConfigProperties.js.dir}/backbone-0.9.1.min.js"></script>
    <script src="${ConfigProperties.js.dir}/tk.ui.js"></script>
    <script src="${ConfigProperties.js.dir}/tk.js"></script>
    <script src="${ConfigProperties.js.dir}/lm.requestApproval.backbone.js"></script>

    
        <div class="approvals">
            <table class="navigation">
                <tbody>
                <tr>
                    <td>
                            <%-- pay calendar group, department and work area filters --%>
                        <tk:approvalFilter calledFrom="LeaveRequestApproval"/>
                    </td>
                    <td align="right">
						<span>
			    			<a href="LeaveApproval.do?">Team Calendar</a>
				    	</span>
                    </td>
                </tr>
                <tr>
                	<td style="white-space:nowrap;" >
                    &nbsp;Principal Ids :
                <select id="selectedPrincipal" name="selectedPrincipal"
                        onchange="this.form.methodToCall.value='selectNewWorkArea'; this.form.submit();">
                    <option value="">Show All</option>
                    <c:forEach var="user" items="${Form.principalIds}">
                        <c:choose>
                            <c:when test="${Form.selectedPrincipal eq user}">
                                <option value="${user}" selected="selected">${user}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${user}">${user}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
        </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div>
        </div>
	    <p id="validation" name="validation"
                           class="validation"></p>
        
        <div>
    	    <tk:calendar cal="${Form.leaveRequestCalendar}" docId="" calType="leaveCalendar" calledFrom="LeaveRequestApproval"/>
        </div>
       <div id="leave-req-app" align="right">
	        Select All <input type="checkbox" name="checkAllApprove" id="checkAllApprove"></input>
        </div>
        <br/><br/>
        <div align="center">
       		 <input type="submit" id="actionOn_Approve" class="approve" value="Approve" name="actionOn_Approve" />
        </div>
        
        <html:textarea property="leaveRequestString" styleId="leaveRequestString" value="${Form.leaveRequestString}"/>
    </html:form>
</tk:tkHeader>

<%-- The leave entry form (dialog) --%>
<div id="cal">
    <div id="dialog-form" class="dialog-form" title="Add Ledgers:">
        <html:form action="/LeaveRequestApproval.do" styleId="leaveRequestApproval-form" >
        
        	<html:hidden property="methodToCall" value="takeAction" styleId="methodToCall"/>
            <html:hidden property="actionList" value="" styleId="actionList"/>
            
            <p id="validation" class="validation" title="Validation">All form fields are .</p>
             <div class="ui-widget timesheet-panel" id="timesheet-panel">
                <table>
                	 <tr>
                        <td><label for="leaveDate">Leave Date:</label></td>
                        <td> 
                        	<label id="leaveDate" name="leaveDate"/>
                        </td>
                    </tr>
                	<tr>
                        <td><label for="principal-user">Principal User:</label></td>
                        <td> 
                        	<label id="principalName" name="principalName"></label>
                        	(<label id="principalId" name="principalId"></label>)
                        </td>
                    </tr>
                    <tr>
                        <td><label for="assignment">Assignment:</label></td>
                        <td> 
                        	<label id="assignmentTitle" name="assignmentTitle"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="leave">Leave:</label></td>
                        <td> 
                        	<label id="leaveCode" name="leaveCode"></label>
                        	(<label id="leaveHours" name="leaveHours"></label>)
                        </td>
                    </tr>
                    <tr>
                        <td><label for="action">Action:</label></td>
                        <td> 
                        	<input type="radio" name="action" id="action"
                                           value="Approve"> Approve
                        	<input type="radio" name="action" id="action"
                                           value="Disapprove"> Disapprove
                            <input type="radio" name="action" id="action"
                                           value="Defer"> Defer
                        </td>
                    </tr>
                     <tr>
                        <td><label for="reason">Reason:</label></td>
                        <td> 
                        	<input type="text" name="reason" id="reason" size="50"/>
                        </td>
                    </tr>
                </table>
             </div>
        </html:form>
    </div>
</div>