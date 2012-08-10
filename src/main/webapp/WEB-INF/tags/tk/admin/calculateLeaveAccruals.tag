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

<channel:portalChannelTop channelTitle="Calculate Leave Accruals" />
<div class="body">
    <div id="content">
        <html:form action="/calculateLeaveAccruals" method="post">
            <table>
                <tr>
                    <td>Principal Name:</td>
                    <td>
                        <html:text property="principalName" size="20" />
                        <kul:lookup boClassName="org.kuali.rice.kim.impl.identity.PersonImpl" fieldConversions="principalName:principalName" />
                    </td>
                </tr>
                <tr>
                    <td>Start Date:</td>
                    <td>
                        <html:text property="startDate" size="12" />
                    </td>
                </tr>
                <tr>
                    <td>End Date:</td>
                    <td>
                        <html:text property="endDate" size="12" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <html:submit property="methodToCall.runAccruals" value="Submit" />
                        <html:submit property="methodToCall.clearAccruals" value="Clear" />
                    </td>
                </tr>
            </table>
        </html:form>
    </div>
</div>
<channel:portalChannelBottom />