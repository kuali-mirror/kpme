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

<c:set var="systemAdmin" value='<%=org.kuali.kpme.tklm.common.TKContext.isSystemAdmin()%>' />

<c:if test="${systemAdmin}">
    <channel:portalChannelTop channelTitle="Initiate Timesheet / Leave Calendar" />
    <html:form action="/initiateDocument.do">
        <div class="body">
            <div id="content">
                <table>
                    <tr>
                        <td>Principal Id:</td>
                        <td>
                            <html:text property="principalId" />
                        </td>
                        <td>
                        	<kul:lookup boClassName="org.kuali.rice.kim.impl.identity.PersonImpl" fieldConversions="principalId:principalId" />
                        </td>
                    </tr>
                    <tr>
                        <td>Calendar Entries Id:</td>
                        <td>
                            <html:text property="hrCalendarEntryId" />
                        </td>
                        <td>
                            <kul:lookup boClassName="org.kuali.kpme.core.bo.calendar.entry.CalendarEntry" fieldConversions="hrCalendarEntryId:hrCalendarEntryId" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:submit property="methodToCall.initiateDocument" value="Initiate" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </html:form>
    <channel:portalChannelBottom />
</c:if>