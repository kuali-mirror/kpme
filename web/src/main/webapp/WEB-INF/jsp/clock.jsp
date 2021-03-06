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
<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${ClockActionForm}" scope="request"/>

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

    <script type="text/javascript" src="themes/kboot/scripts/kboot.2.4.2.min.js"></script>
    <script src="${ConfigProperties.js.dir}/underscore-1.3.1.min.js"></script>
    <script src="${ConfigProperties.js.dir}/backbone-0.9.1.min.js"></script>
    <script src="${ConfigProperties.js.dir}/clock.js"></script>


	<html:form action="/Clock.do">
    	<html:hidden property="methodToCall" value=""/>
    	<html:hidden property="currentClockAction" styleId="clockAction"/>
    	<html:hidden property="lastClockedInTime" value="${Form.lastClockTimestamp}" styleId="lastClockedInTime"/>
    	<html:hidden property="currentServerTime" value="${Form.currentServerTime}" styleId="currentServerTime"/>
    	<html:hidden property="currentUserUTCOffset" value="${Form.currentUserUTCOffset}" styleId="currentUserUTCOffset"/>
    
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

                    <c:choose>
                        <c:when test="${Form.proxyClockAction}">
                            <c:set var="clockActionIdSuffix" value = "-proxy"></c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="clockActionIdSuffix" value = "-actualuser"></c:set>
                        </c:otherwise>
                    </c:choose>

        			<tr class="footer">
        				<td colspan="2" align="center">
	        				<c:choose>
	        					<c:when test="${Form.showClockButton}">
		                            <c:choose>
		                                <c:when test="${Form.clockButtonEnabled}">
		                                    <input id="clock-button${clockActionIdSuffix}" type="submit" class="button" value="${clockActionDescription}" name="clockAction" onclick="this.form.methodToCall.value='clockAction';return false;"/>
		                                </c:when>
		                                <c:otherwise>
		                                    <input disabled id="clock-button" type="submit" class="button" value="${clockActionDescription}" name="clockAction" />
		                                </c:otherwise>
		                            </c:choose>
	        					</c:when>
	        				</c:choose>
                            <c:choose>
        						<c:when test="${Form.currentClockAction eq 'CO'}">
                                    <c:choose>
                                        <c:when test="${Form.showLunchButton}">
                                            <c:choose>
                                                <c:when test="${Form.clockButtonEnabled}">
                                                    <input id = "clockout-button${clockActionIdSuffix}" type="submit" class="button" value="Take Lunch" name="lunchOut" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LO';return false;"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input disabled type="submit" class="button" value="Take Lunch" name="lunchOut" />
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
                                            <input disabled type="submit" class="button" value="Return From Lunch" name="lunchIn"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${Form.clockButtonEnabled}">
                                    <input type="button" class="button" value="Missed Punch" name="missedPunch" onClick="javascript: showLightboxUrl(extractUrlBase() + '/kpme/missedPunch?&methodToCall=start&viewId=MissedPunch-SubmitView&missedPunch.timesheetDocumentId=' + ${Form.timesheetDocument.documentId}, {minHeight: 600, maxWidth: 600, closeBtn: false})" />
                                </c:when>
                                <c:otherwise>
                                    <input disabled type="button" class="button" value="Missed Punch" name="missedPunch"  />
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
                                            name="distributeTime" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
        				</td>
        			</tr>
        		</table>
        	</div>
            <tk:note/>
        	<div id="missed-punch-dialog" title="Missed Punch" style="display:none;" />

            <div id="confirm-proxy-clock-action-dialog" title="Confirm Action" class="dialog-form">
                    This Clock Action will be done on behalf of ${Form.proxyClockActionTargetUser}

                    ${proxyClockActionCssClass}
            </div>

        </c:if>
	</html:form>
</tk:tkHeader>

