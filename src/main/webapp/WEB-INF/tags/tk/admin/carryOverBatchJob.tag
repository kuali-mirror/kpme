<%--
 Copyright 2007-2009 The Kuali Foundation
 
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
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<c:set var="systemAdmin" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isSystemAdmin()%>' />

<c:if test="${systemAdmin}">
    <channel:portalChannelTop channelTitle="Run Carry Over Batch Job" />
    <html:form action="/carryOverBatchJob.do">
        <div class="body">
            <div id="content">
                <table>
                    <tr>
                        <td>Leave Plan:</td>
                        <td>
                            <html:text property="leavePlan" />
                        </td>
                        <td>
                        	<kul:lookup boClassName="org.kuali.hr.lm.leaveplan.LeavePlan" fieldConversions="leavePlan:leavePlan" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:submit property="methodToCall.runCarryOverBatchJob" value="Run" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </html:form>
    <channel:portalChannelBottom />
</c:if>