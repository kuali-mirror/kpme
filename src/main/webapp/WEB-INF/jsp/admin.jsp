<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<c:set var="Form" value="${AdminActionForm}" scope="request"/>
<c:set var="KualiForm" value="${AdminActionForm}" scope="request"/>

<tk:tkHeader tabId="admin">
<div id="admin-page">
<div id="content">
    <table>
        <tr class="header">
            <td><b>Maintenance pages</b></td>
        </tr>
        <tr>
            <td>
                <b>Administrative</b>
                <ul>
                    <c:if test="${Form.user.systemAdmin || Form.user.globalViewOnly}">
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Account&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Account</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Chart&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Chart</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.department.Department&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Department</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.ObjectCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Object Code</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Organization&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Organization</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.location.Location&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Location</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.ProjectCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Project Code</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.SubAccount&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Sub Account</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.SubObjectCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Sub Object Code</a>
                        </li>
                    </c:if>
                </ul>
                <strong>KPME</strong>
                <ul>
                    <c:if test="${Form.user.systemAdmin || Form.user.globalViewOnly}">
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.earncodesec.EarnCodeSecurity&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Earn Code Security</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earncode.EarnCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Earn Code</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earncodegroup.EarnCodeGroup&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Earn Code Group</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.calendar.Calendar&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Calendar</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.calendar.CalendarEntries&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Calendar Entry</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.job.Job&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Job</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.position.Position&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Position</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.paygrade.PayGrade&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Pay Grade</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paytype.PayType&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Pay Type</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.salgroup.SalGroup&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Salary Group</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.principal.PrincipalHRAttributes&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Principal HR Attributes</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.assignment.Assignment&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Assignment</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.roles.TkRoleGroup&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                User Role Maintenance</a>
                        </li>
                    </c:if>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            WorkArea Maintenance</a>
                    </li>
                </ul>
                <strong>Time Keeping</strong>
                <ul>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.TimeOffAccrual&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Time Off Accrual</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.timesheet.TimeSheetInitiate&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Time Sheet Initiate</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clock.location.ClockLocationRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Clock Location Rule</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.dept.lunch.DeptLunchRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Department Lunch Deduction Rule</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.collection.rule.TimeCollectionRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Time Collection Rule</a>
                    </li>
                    <c:if test="${Form.user.systemAdmin || Form.user.globalViewOnly}">
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Daily OverTime Rule</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.graceperiod.rule.GracePeriodRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Grace Period Rule</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                Shift Differential Rule</a>
                        </li>
                        <li>
                            <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.syslunch.rule.SystemLunchRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                                System Lunch Rule</a>
                        </li>
                    </c:if>
                    <c:if test="${Form.user.systemAdmin}">
                        <li>
                            <a href="kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRuleGroup&tkWeeklyOvertimeRuleGroupId=1&returnLocation=${ConfigProperties.application.url}/Admin.do&methodToCall=edit">
                                Weekly Overtime Rule</a>
                        </li>
                    </c:if>
                </ul>

                <strong>Leave Maintenance</strong>
                <ul>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.accrual.AccrualCategory&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Accrual Category</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.employeeoverride.EmployeeOverride&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Employee Override</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.leaveadjustment.LeaveAdjustment&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Leave Adjustment</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.leavecode.LeaveCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Leave Code</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.leaveplan.LeavePlan&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Leave Plan</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.leavedonation.LeaveDonation&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            Leave Donation</a>
                    </li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.timeoff.SystemScheduledTimeOff&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                            System Scheduled Time Off</a>
                    </li>
                </ul>

                <b>Inquiries</b>
                <ul>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.timeblock.TimeBlock&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Time
                            Block Inquiry</a></li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.timeblock.TimeBlockHistoryDetail&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Time
                            Block History Inquiry</a></li>
                    <li>
                        <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clocklog.ClockLog&returnLocation=${ConfigProperties.application.url}/Admin.do&hideReturnLink=true&docFormKey=88888888&active=Y">Clock
                            Log</a></li>
                    <li><a href="${Form.workflowUrl}/DocumentSearch.do?docTypeFullName=MissedPunchDocumentType">Missed
                        Punch</a></li>
                </ul>

                <b>Batch Jobs</b>
                <ul>
                    <li><a href="BatchJob.do">BatchJob</a></li>
                </ul>
                <c:if test="${Form.user.systemAdmin}">
                <strong>Utility</strong>
                <ul>
                    <li><a href="CalendarEntry.do">Calendar Entry</a></li>
                </ul>
            </c:if></tr>
        </tr>
    </table>
</div>

<c:if test="${Form.canChangePrincipal}">

    <div id="content">
        <html:form action="/Admin" method="post">
            <html:hidden property="methodToCall" value=""/>
            <table>
                <tr class="header">
                    <td><strong>Change Target Person</strong></td>
                </tr>
                <tr>
                    <td><html:text property="changeTargetPrincipalName" size="20"/>
                        <kul:lookup
                            boClassName="org.kuali.rice.kim.bo.impl.PersonImpl"
                            fieldConversions="principalName:changeTargetPrincipalName"
                            lookupParameters="" baseLookupUrl="./kr/" />

                        <input type="button" class="button" value="Submit" name="changeEmployee" onclick="this.form.methodToCall.value='changeEmployee'; this.form.submit();" />
                        <input type="button" class="button" value="Clear" name="clearChangeEmployee" onclick="this.form.methodToCall.value='clearChangeEmployee'; this.form.submit();" />
                    </td>
                </tr>
                <c:if test="${Form.user.systemAdmin}">
                    <tr class="header">
                        <td><strong>Backdoor</strong></td>
                    </tr>
                    <tr>
                        <td><html:text property="backdoorPrincipalName" size="20"/>
                            <kul:lookup boClassName="org.kuali.rice.kim.bo.impl.PersonImpl"
                                        fieldConversions="principalName:backdoorPrincipalName"
                                        lookupParameters="" baseLookupUrl="./kr/" />
                            <input type="button" class="button" value="Submit" name="backdoor" onclick="this.form.methodToCall.value='backdoor'; this.form.submit();"/>
                            <input type="button" class="button" value="Clear" name="clearBackdoor" onclick="this.form.methodToCall.value='clearBackdoor'; this.form.submit();"/>
                        </td>
                    </tr>
                    <tr class="header">
                        <td><strong>Delete Timesheet</strong></td>
                    </tr>
                    <tr>
                        <td><html:text property="deleteDocumentId" size="20"/>
                            <input type="button" class="button" value="Submit" name="deleteTimesheet" onclick="this.form.methodToCall.value='deleteTimesheet'; this.form.submit();"/>
                        </td>
                    </tr>
                    <tr class="header">
                        <td><strong>Calculate Leave Accruals</strong></td>
                    </tr>
                    <tr>
                        <td>
                            Principal Id:
                                <html:text property="accrualPrincipalId" size="20"/>
                            <kul:lookup
                                    boClassName="org.kuali.rice.kim.bo.impl.PersonImpl"
                                    fieldConversions="principalName:accrualPrincipalId"
                                    lookupParameters="" baseLookupUrl="./kr/" />
                            <br/>
                            <script>
//                                $(function () {
//                                    $("#startdatepicker").datepicker();
//                                });
                            </script>
                            Start Date: <input type="text" name="formStartDate" id="startdatepicker" size='12' value='${Form.formStartDate}'/>
                            <br/>
                            <script>
//                                $(function () {
//                                    $("#enddatepicker").datepicker();
//                                });
                            </script>
                            End Date: <input type="text" name="formEndDate" id="enddatepicker" size='12' value='${Form.formEndDate}'/>
                            <br/>
                            <input type="button" class="button" value="Submit" name="runAccruals" onclick="this.form.methodToCall.value='runAccruals'; this.form.submit();"/>
                            <input type="button" class="button" value="Clear" name="clearAccruals" onclick="this.form.methodToCall.value='clearAccruals'; this.form.submit();"/>

                        </td>
                    </tr>
                </c:if>

            </table>
        </html:form>
    </div>
</c:if>
</div>
</tk:tkHeader>
