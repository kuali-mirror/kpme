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
    <channel:portalChannelTop channelTitle="Run Batch Job" />
    <html:form action="/batchJob.do">
        <div class="body">
            <div id="content">
                <table>
                    <tr>
                        <td>Batch Job:</td>
                        <td colspan="2">
                            <select name="selectedBatchJob" id="selectedBatchJob">
                                <c:forEach var="job" items="${BatchJobActionForm.batchJobNames}">
                                    <option value="${job}">${job}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Pay calendar period:</td>
                        <td>
                            <html:text property="hrPyCalendarEntryId" />
                        </td>
                        <td>
                        	<kul:lookup boClassName="org.kuali.hr.time.paycalendar.PayCalendarEntries" fieldConversions="hrPyCalendarEntriesId:hrPyCalendarEntryId" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:submit property="methodToCall.runBatchJob" value="Run" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </html:form>
    <channel:portalChannelBottom />
        
    <channel:portalChannelTop channelTitle="Batch Job Entry Status" />
    <html:form action="/batchJob.do">
        <div class="body">
            <div id="content">
                <table>
                    <tr>
                        <td>Job Id:</td>
                        <td>
                            <html:text property="batchJobId"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Job Name:</td>
                        <td>
                            <html:text property="batchJobName"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Job Status:</td>
                        <td>
                            <html:text property="batchJobEntryStatus"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Pay Calendar Id:</td>
                        <td>
                            <html:text property="hrPyCalendarEntryId"/>
                        </td>
                    </tr>
                    <tr>
                        <td>IP Address:</td>
                        <td>
                            <html:text property="ipAddress"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Document Id:</td>
                        <td>
                            <html:text property="documentId"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Principal Id:</td>
                        <td>
                            <html:text property="principalId"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:submit property="methodToCall.getBatchJobEntryStatus" value="Search" />
                        </td>
                    </tr>
                </table>

                <display:table name="${BatchJobActionForm.batchJobEntries}" excludedParams="*" pagesize="10" requestURI="batchJob.do" requestURIcontext="false" id="r">
                    <display:column property="tkBatchJobEntryId" title="Job Entry Id"/>
                    <display:column property="batchJobName" title="Job Name"/>
                    <display:column property="batchJobEntryStatus" title="Job Entry Status"/>
                    <display:column property="hrPyCalendarEntryId" title="Pay Calendar Id"/>
                    <display:column title="IP Address">
                        <tk:ipAddress batchJobEntryId="${r.tkBatchJobEntryId}"
                                      selectedIpAdd="${r.ipAddress}"/>
                    </display:column>
                    <display:column property="documentId" title="Document Id"/>
                    <display:column property="clockLogId" title="Clock Log Id"/>
                    <display:column property="principalId" title="Principal Id"/>
                </display:table>
            </div>
        </div>
    </html:form>
    <channel:portalChannelBottom />
</c:if>