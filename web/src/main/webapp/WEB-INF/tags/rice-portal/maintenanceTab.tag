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
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
    <c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>' />
    <c:set var="globalViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isGlobalViewOnly()%>' />

    <c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>' />
    <c:set var="locationViewOnly" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationViewOnly()%>' />


    <c:set var="KOHRAcademicHrAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRAcademicHrAdmin()%>' />
    <c:set var="KOHRInstitutionViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRInstitutionViewOnly()%>' />
    <c:set var="KOHRLocationAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRLocationAdmin()%>' />
    <c:set var="KOHRLocationViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRLocationViewOnly()%>' />
    <c:set var="KOHRLocationAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRLocationAdmin()%>' />
    <c:set var="KOHROrgAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHROrgAdmin()%>' />
    <c:set var="KOHROrgViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHROrgViewOnly()%>' />
    <c:set var="HRDepartmentAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHRDepartmentAdmin()%>' />
    <c:set var="HRDepartmentViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHRDepartmentViewOnly()%>' />
    <c:set var="HRInstitutionApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHRInstitutionApprover()%>' />
    <c:set var="academicHRInstitutionApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetAcademicHRInstitutionApprover()%>' />
    <c:set var="budgetApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetBudgetApprover()%>' />
    <c:set var="payrollApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetPayrollApprover()%>' />
    <c:set var="HRlocationApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHRLocationApprover()%>' />
    <c:set var="academicHRLocationApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetAcademicHRLocationApprover()%>' />
    <c:set var="fiscalLocationApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetFiscalLocationApprover()%>' />
    <c:set var="HROrgApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHROrgApprover()%>' />
    <c:set var="fiscalOrgApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetFiscalOrgApprover()%>' />
    <c:set var="departmentApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetDepartmentApprover()%>' />
    <c:set var="fiscalDeptApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetFiscalDepartmentApprover()%>' />
    <c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>' />
    <c:set var="locationViewOnly" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationViewOnly()%>' />
</c:if>


<c:set var = "allowed" value = "false"/>
<c:if test="${systemAdmin || globalViewOnly  || locationAdmin || locationViewOnly || KOHRInstitutionAdmin || KOHRAcademicHrAdmin || KOHRInstitutionViewOnly || KOHRLocationAdmin || KOHRLocationViewOnly ||
    	KOHRLocationAdmin || KOHROrgAdmin || KOHROrgViewOnly || HRDepartmentAdmin || HRDepartmentViewOnly || HRInstitutionApprover ||
    	academicHRInstitutionApprover || budgetApprover || payrollApprover || HRlocationApprover || academicHRLocationApprover || fiscalLocationApprover ||
    	HROrgApprover || fiscalOrgApprover || departmentApprover || fiscalDeptApprover || locationAdmin || locationViewOnly}">

    <c:set var = "allowed" value = "true"/>

</c:if>


<c:if test="${not allowed}">
    <jsp:forward page="portal.do?selectedTab=main"/>
</c:if>

<td class="content" valign="top">
    <admin:hrPayroll />
    <admin:hrData />
</td>
<td class="content" valign="top">
    <admin:positionManagement />
    <admin:personManagement/>
</td>
<td class="content" valign="top">
    <admin:financial />
    <admin:workflow />
    <admin:notification />
</td>
