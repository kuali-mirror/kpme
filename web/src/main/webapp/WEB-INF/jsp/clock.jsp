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
<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${ClockActionForm}" scope="request"/>

<script type="text/javascript">
var tdocid = ${Form.timesheetDocument.documentId} ;
</script>

<c:choose>
	<c:when test="${Form.currentClockAction eq 'CI'}">
		<c:set var="clockActionDescription" value="Clock In"/>
		<c:set var="lastClockActionMessage" value="Clocked out since : "/>
	</c:when>
	<c:when test="${Form.currentClockAction eq 'LI'}">
	    <c:set var="clockActionDescription" value="Clock Out"/>
	    <c:set var="lastClockActionMessage" value="At lunch since : "/>
	</c:when>
	<c:when test="${Form.currentClockAction eq 'LO'}">
	<c:set var="clockActionDescription" value="Clock Out"/>
		<c:set var="lastClockActionMessage" value="Returned from lunch since : "/>
	</c:when>
	<c:otherwise>
		<c:set var="clockActionDescription" value="Clock Out"/>
		<c:set var="lastClockActionMessage" value="Clocked in since : "/>
	</c:otherwise>
</c:choose>
<c:if test="${Form.lastClockTimestamp eq null}">
    <c:set var="clockActionDescription" value="Clock In"/>
    <c:set var="lastClockActionMessage" value="No previous clock information"/>
</c:if>

<tk:tkHeader tabId="clock">
    <script type="text/javascript" src="krad/plugins/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="krad/plugins/jqueryUI/jquery-ui-1.9.2.js"></script>
    <script type="text/javascript" src="krad/plugins/jgrowl/jquery.jgrowl.js"></script>
    <script type="text/javascript" src="krad/plugins/blockUI/jquery.blockUI.js"></script>
    <script type="text/javascript" src="krad/plugins/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="krad/plugins/tooltip/jquery.bubblepopup.v2.3.1.js"></script>
    <script type="text/javascript" src="krad/plugins/dirtyform/jquery.dirtyform.js"></script>
    <script type="text/javascript" src="krad/plugins/scrollto/jquery.scrollTo-1.4.3-min.js"></script>
    <script type="text/javascript" src="krad/plugins/rice/datatables/jquery.dataTables.js"></script>
    <script type="text/javascript" src="krad/plugins/rice/datatables/ZeroClipboard.js"></script>
    <script type="text/javascript" src="krad/plugins/rice/datatables/TableTools.js"></script>
    <script type="text/javascript" src="krad/plugins/rice/datatables/jquery.dataTables.rowGrouping.js"></script>
    <script type="text/javascript" src="krad/plugins/fancybox/jquery.fancybox.pack.js"></script>
    <script type="text/javascript" src="krad/scripts/krad.variables.js"></script>
    <script type="text/javascript" src="krad/scripts/krad.message.js"></script>
    <script type="text/javascript" src="krad/scripts/krad.widget.js"></script>
    <script type="text/javascript" src="krad/scripts/krad.url.js"></script>
    <script type="text/javascript" src="krad/scripts/krad.utility.js"></script>
    <script type="text/javascript" src="krad/scripts/krad.initialize.js"></script>

	<html:form action="/Clock.do">
    	<html:hidden property="methodToCall" value=""/>
    	<html:hidden property="currentClockAction" styleId="clockAction"/>
    	<html:hidden property="lastClockedInTime" value="${Form.lastClockTimestamp}" styleId="lastClockedInTime"/>
    	<html:hidden property="currentServerTime" value="${Form.currentServerTime}" styleId="currentServerTime"/>
    	<html:hidden property="userSystemOffsetServerTime" value="${Form.userSystemOffsetServerTime}" styleId="userSystemOffsetServerTime"/>
    
    	<div id="errorMessage" style="color:red;font-size:14px">
    		<c:if test="${Form.errorMessage ne null}">
    			<b>Error</b> : ${Form.errorMessage}
    		</c:if>
    	</div>
    	
    	<c:if test="${not empty Form.documentId}">
        	<div id="clock">
        		<table>
        			<tr class="header"><td colspan="2"><bean:message key="clock.title"/></td></tr>
        			<tr>
        				<td class="sub-header"><bean:message key="clock.currentTime"/> : </td>
        				<td><div class="jClock"></div></td>
        			</tr>
        			<tr>
        				<td class="sub-header"><bean:message key="clock.workStatus"/> : </td>
        				<td>${lastClockActionMessage}
        				<fmt:timeZone value="${Form.targetUserTimezone}"><fmt:formatDate type="both" value="${Form.lastClockTimeWithZone}" pattern="EEE, MMMM d yyyy hh:mm:ss a, zzzz"/></fmt:timeZone>
        				</td>
        			</tr>
        			<tr>
        				<td class="sub-header"><bean:message key="clock.clockAssignment"/> : </td>
        				<td>
        					<tk:assignment assignments="${Form.assignmentDescriptions}"/>
        				</td>
        			</tr>
        			<tr class="footer">
        				<td colspan="2" align="center">
                            <c:choose>
                                <c:when test="${Form.clockButtonEnabled}">
                                    <input id="clock-button" type="submit" class="button" value="${clockActionDescription}" name="clockAction" onclick="this.form.methodToCall.value='clockAction';"/>
                                </c:when>
                                <c:otherwise>
                                    <input disabled id="clock-button" type="submit" class="button" value="${clockActionDescription}" name="clockAction" onclick="this.form.methodToCall.value='clockAction';"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
        						<c:when test="${Form.currentClockAction eq 'CO'}">
                                    <c:choose>
                                        <c:when test="${Form.showLunchButton}">
                                            <c:choose>
                                                <c:when test="${Form.clockButtonEnabled}">
                                                    <input type="submit" class="button" value="Take Lunch" name="lunchOut" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LO';"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input disabled type="submit" class="button" value="Take Lunch" name="lunchOut" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LO';"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
        						</c:when>
        						<c:when test="${Form.currentClockAction eq 'LI'}">
                                    <c:choose>
                                        <c:when test="${Form.clockButtonEnabled}">
                                            <input type="submit" class="button" value="Return From Lunch" name="lunchIn" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LI';"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input disabled type="submit" class="button" value="Return From Lunch" name="lunchIn" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LI';"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${Form.clockButtonEnabled}">
                                    <input type="button" class="button" value="Missed Punch" name="missedPunch" onClick="javascript: showLightboxUrl(extractUrlBase() + '/kpme/missedPunch?&methodToCall=start&viewId=MissedPunch-SubmitView&missedPunch.timesheetDocumentId=' + tdocid, {minHeight: 500, maxWidth: 600})" />
                                </c:when>
                                <c:otherwise>
                                    <input disabled type="button" class="button" value="Missed Punch" name="missedPunch" onClick="javascript: showLightboxUrl(extractUrlBase() + '/kpme/missedPunch?&methodToCall=start&viewId=MissedPunch-SubmitView&missedPunch.timesheetDocumentId=' + tdocid, {minHeight: 500, maxWidth: 600})" />
                                </c:otherwise>
                            </c:choose>
        					<c:if test="${Form.showDistributeButton}">
                                <c:choose>
                                    <c:when test="${Form.clockButtonEnabled}">
                                        <input id="distribute-button" type="button" class="button" value="Distribute Time Blocks"
                                            name="distributeTime" onclick="javascrpt: window.open(extractUrlBase()+'/Clock.do?methodToCall=distributeTimeBlocks', 'distributePopup')"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input disabled id="distribute-button" type="button" class="button" value="Distribute Time Blocks"
                                            name="distributeTime" onclick="javascrpt: window.open(extractUrlBase()+'/Clock.do?methodToCall=distributeTimeBlocks', 'distributePopup')"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
        				</td>
        			</tr>
        		</table>
        	</div>
        	<div id="missed-punch-dialog" title="Missed Punch" style="display:none;" />
            <tk:note/>
        </c:if>
	</html:form>
</tk:tkHeader>

