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

<c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>' />

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
                        	<kul:lookup boClassName="org.kuali.kpme.core.bo.calendar.entry.CalendarEntry" fieldConversions="hrCalendarEntryId:hrPyCalendarEntryId" />
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
</c:if>