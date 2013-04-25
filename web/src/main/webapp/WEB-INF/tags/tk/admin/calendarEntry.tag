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

<c:set var="systemAdmin" value='<%=org.kuali.hr.tklm.time.util.TKContext.isSystemAdmin()%>' />

<c:if test="${systemAdmin}">
    <channel:portalChannelTop channelTitle="Create Calendar Entry" />
    <div class="body">
        <div id="content">
            <html:form action="/calendarEntry.do">
                <table>
                    <tr>
                        <td>Number of Periods:</td>
                        <td>
                            <html:text property="noOfPeriods" />
                        </td>
                    </tr>
                    <tr>
                        <td>Pay Calendar Period:</td>
                        <td>
                            <html:text property="hrPyCalendarEntryId" />
                        </td>
                        <td>
                            <kul:lookup boClassName="org.kuali.hr.core.calendar.CalendarEntry" fieldConversions="hrCalendarEntryId:hrPyCalendarEntryId" />
                        </td>
                    </tr>
                    <tr>
                        <td>Calendar Frequency:</td>
                        <td>
                            <select name="calendarEntryPeriodType" id="calendarEntryPeriodType">
                                <option value="W">Weekly</option>
                                <option value="B">Biweekly</option>
                                <option value="S">Semi Monthly</option>
                                <option value="M">Monthly</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:submit property="methodToCall.createCalendarEntry" value="Submit" />
                        </td>
                    </tr>
                </table>
            </html:form>
            ${CalendarEntryActionForm.message}
        </div>
    </div>
    <channel:portalChannelBottom />
</c:if>