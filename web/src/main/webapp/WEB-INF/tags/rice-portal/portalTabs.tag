<%--
 Copyright 2005-2009 The Kuali Foundation
 
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

<%@ attribute name="selectedTab" required="true"%>

<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
    <c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>' />
    <c:set var="globalViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isGlobalViewOnly()%>' />

    <c:set var="kohrAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserKOHRInstitutionAdmin()%>' />

    <c:set var="academicAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserKOHRAcademicHrAdmin()%>' />

    <c:set var="institutionViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserKOHRInstitutionViewOnly()%>' />


    <c:set var = "KOHRLocationViewOnly" value = "<%=org.kuali.kpme.core.util.HrContext.isUserKOHRLocationViewOnly()%>"/>
    <c:set var = "KOHRLocationAdmin" value = "<%=org.kuali.kpme.core.util.HrContext.isUserKOHRLocationAdmin()%>"/>


    <c:set var="orgViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserKOHROrgViewOnly()%>' />
    <c:set var="orgAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isTargetKOHROrgAdmin()%>' />

    <c:set var="deptAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserHRDepartmentAdmin()%>' />
    <c:set var="deptViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserHRDepartmentViewOnly()%>' />


    <c:set var="departmentAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isDepartmentAdmin()%>' />


    <c:set var="departmentViewOnly" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isDepartmentViewOnly()%>' />
    <c:set var="approver" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetAnyApprover()%>' />
    <c:set var="payrollProcessor" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetAnyPayrollProcessor()%>' />
    <c:set var="orgApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHROrgApprover()%>' />
    <c:set var="reviewer" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetReviewer()%>' />
    <c:set var="targetActiveEmployee" value='<%=org.kuali.kpme.core.util.HrContext.isTargetActiveEmployee()%>' />
    <c:set var="targetSynchronous" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isTargetSynchronous()%>' />
    <c:set var="fiscalDeptApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserFiscalDepartmentApprover()%>' />
    <c:set var="fiscalOrgApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserFiscalOrgApprover()%>' />


</c:if>
<div id="tabs" class="tabposition">
	<ul>
	
		<%-- Main Menu --%>
		<c:if test='${selectedTab == "main"}'>
			<li class="red">
				<a class="red" href="portal.do?selectedTab=main"
					title="Menu">Menu</a>
			</li>
		</c:if>
		<c:if test='${selectedTab != "main"}'>
			<c:if test="${empty selectedTab}">
				<li class="red">
					<a class="red" href="portal.do?selectedTab=main"
						title="Menu">Menu</a>
				</li>
			</c:if>
			<c:if test="${!empty selectedTab}">
				<li class="green">
					<a class="green" href="portal.do?selectedTab=main"
						title="Menu">Menu</a>
				</li>
			</c:if>
		</c:if>

        <%-- Maintenance --%>
        <c:if test="${systemAdmin || globalViewOnly || KOHRLocationViewOnly || KOHRLocationAdmin || academicAdmin || institutionViewOnly || orgViewOnly || orgAdmin || deptAdmin || deptViewOnly }">
            <c:if test='${selectedTab == "kpmemaintenance"}'>
                <li class="red">
                    <a class="red" href="portal.do?selectedTab=kpmemaintenance"
                        title="Maintenance">KHR Maintenance</a>
                </li>
            </c:if>
            <c:if test='${selectedTab != "kpmemaintenance"}'>
                <li class="green">
                    <a class="green"
                        href="portal.do?selectedTab=kpmemaintenance"
                        title="KHR Maintenance">KHR Maintenance</a>
                </li>
            </c:if>
        </c:if>

		<%-- Administration --%>
		<c:if test='${selectedTab == "riceadministration"}'>
			<li class="red">
				<a class="red" href="portal.do?selectedTab=riceadministration"
					title="Rice Administration">Rice Administration</a>
			</li>
		</c:if>
		<c:if test='${selectedTab != "riceadministration"}'>
			<li class="green">
				<a class="green"
					href="portal.do?selectedTab=riceadministration"
					title="Rice Administration">Rice Administration</a>
			</li>
		</c:if>

	</ul>
</div>
