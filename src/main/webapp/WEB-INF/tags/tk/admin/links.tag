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

<channel:portalChannelTop channelTitle="KPME Links" />
<div class="body">
    <ul class="chan">
        <li>
            <a href="Clock.do">Clock</a>
        </li>
        <li>
            <a href="DepartmentAdmin.do">Department Admin</a>
        </li>
        <li>
            <a href="LeaveCalendar.do">Leave Calendar</a>
        </li>
        <li>
            <a href="TimeApproval.do?methodToCall=loadApprovalTab">Time Approval</a>
        </li>
        <li>
            <a href="LeaveApproval.do?methodToCall=loadApprovalTab">Leave Approval</a>
        </li>
        <li>
            <a href="TimeDetail.do">Time Detail</a>
        </li>
        <li>
            <a href="PersonInfo.do">Person Info</a>
        </li>
	</ul>
</div>
<channel:portalChannelBottom />