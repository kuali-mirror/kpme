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
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp" %>

<c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>'/>
<c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>'/>
<c:if test="${systemAdmin || locationAdmin}">
    <div class="portlet">
        <div class="header">Calculate Leave Accruals</div>
        <div class="body">
            <div id="content">
                <html:form action="/calculateLeaveAccruals" method="post">
                    <table>
                        <tr>
                            <td>Principal Name:</td>
                            <td>
                                <html:text property="principalName" size="20"/>
                                <kul:lookup boClassName="org.kuali.rice.kim.impl.identity.PersonImpl"
                                            fieldConversions="principalName:principalName"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Or</td>
                        </tr>
                        <tr>
                            <td>Leave Plan ID:</td>
                            <td>
                                <html:text property="leavePlanId" size="20"/>
                                <kul:lookup boClassName="org.kuali.kpme.core.leaveplan.LeavePlanBo"
                                            fieldConversions="lmLeavePlanId:leavePlanId"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Start Date:</td>
                            <td>
                                <html:text property="startDate" size="12"/>
                            </td>
                        </tr>
                        <tr>
                            <td>End Date:</td>
                            <td>
                                <html:text property="endDate" size="12"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <html:submit property="methodToCall.runAccruals" value="Submit"/>
                                <html:submit property="methodToCall.clearAccruals" value="Clear"/>
                            </td>
                        </tr>
                    </table>
                    ${CalculateLeaveAccrualsForm.message}
                </html:form>
            </div>
        </div>
    </div>
</c:if>