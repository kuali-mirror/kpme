<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${AdminActionForm}" scope="request"/>
<c:set var="KualiForm" value="${AdminActionForm}" scope="request"/>

<tk:tkHeader tabId="admin">
<div id="admin-page">
    <div id="content">
        <table>
            <tr class="header"><td><b>Maintenance pages</b></td></tr>
            <tr>
                <td>
                    <b>Rules</b>
		             <ul>
		                <li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clock.location.ClockLocationRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Clock Location Rule</a></li>
		                <li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.dept.lunch.DeptLunchRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Department Lunch Deduction Rule</a></li>
		                <li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.collection.rule.TimeCollectionRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">TimeCollection Rule</a></li>
		                <c:if test="${Form.user.systemAdmin || Form.user.globalViewOnly}">
		                <li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Daily OverTime Rule</a></li>
		                <li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.graceperiod.rule.GracePeriodRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Grace Period Rule</a></li>
		                <li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Shift Differential Rule</a></li>
		                <li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.syslunch.rule.SystemLunchRule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">System Lunch Rule</a></li>
		                <li><a href="kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRuleGroup&tkWeeklyOvertimeRuleGroupId=1&returnLocation=${ConfigProperties.application.url}/Admin.do&methodToCall=edit">Weekly Overtime Rule</a></li>
		           		</c:if>
		            </ul>
                    <b>Administrative Activities</b>
                     <ul>
							<c:if test="${Form.user.systemAdmin || Form.user.globalViewOnly}">
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Account&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Account</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.AccrualCategory&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Accrual
										Category</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.assignment.Assignment&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Assignment</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Chart&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Chart</a>
								</li>
							
								<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.department.Department&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Department</a>
								</li>
							
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.dept.earncode.DepartmentEarnCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Department
										Earn Code</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earncode.EarnCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Earn
										Code</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earngroup.EarnGroup&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Earn
										Group</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.holidaycalendar.HolidayCalendar&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Holiday
										Calendar</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.job.Job&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Job</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.location.Location&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Location</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.ObjectCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Object
										Code</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Organization&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Organization</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paycalendar.PayCalendar&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Pay
										Calendar</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paycalendar.PayCalendarEntries&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Pay
										Calendar Entry</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.paygrade.PayGrade&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Pay
										Grade</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paytype.PayType&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Pay
										Type</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.position.Position&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Position</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.principal.calendar.PrincipalCalendar&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Principal
										Calendar</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.ProjectCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Project
										Code</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.salgroup.SalGroup&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Salary
										Group</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.SubAccount&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Sub
										Account</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.SubObjectCode&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Sub
										Object Code</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.TimeOffAccrual&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Time
										Off Accrual</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.timesheet.TimeSheetInitiate&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Time
										Sheet Initiate</a>
								</li>
								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.roles.TkRoleGroup&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">User
										Role Maintenance</a>
								</li>

								<li><a
									href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workschedule.WorkSchedule&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Work
										Schedule</a>
								</li>
							</c:if>

							<li>
								<a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">WorkArea Maintenance</a>
							</li>
						</ul>
                    <b>Inquiries</b>
                    <ul>
                    	<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.timeblock.TimeBlock&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Time Block Inquiry</a></li>
                    	<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.timeblock.TimeBlockHistory&returnLocation=${ConfigProperties.application.url}/Admin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Time Block History Inquiry</a></li>
                    	<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clocklog.ClockLog&returnLocation=${ConfigProperties.application.url}/Admin.do&hideReturnLink=true&docFormKey=88888888&active=Y">Clock Log</a></li>
                    	<li><a href="${Form.workflowUrl}/DocumentSearch.do?docTypeFullName=MissedPunchDocumentType">Missed Punch</a></li>
                    </ul>
                    
               
            </tr>
        </table>
    </div>

    <c:if test="${Form.user.systemAdmin || Form.user.globalViewOnly}">
    	<div id="content">
	   	<html:form action="/Admin" method="post">
	   	<html:hidden property="methodToCall" value=""/>
		   <table>
		   		<c:if test="${Form.user.systemAdmin}">
	            <tr class="header"><td><b>Backdoor</b></td></tr>
	            <tr>
	                <td>
						<html:text property="backdoorPrincipalName" size="20" />
						<input type="button" class="button" value="Submit" name="backdoor" onclick="this.form.methodToCall.value='backdoor'; this.form.submit();">
						<input type="button" class="button" value="Clear" name="clearBackdoor" onclick="this.form.methodToCall.value='clearBackdoor'; this.form.submit();">
	                </td>
	            </tr>
	            <tr/>
	            </c:if>
	            <tr class="header"><td><b>Change Target Person</b></td></tr>
	            <tr>
	                <td>
						<html:text property="changeTargetPrincipalId" size="20" />
						<kul:lookup boClassName="org.kuali.rice.kim.bo.impl.PersonImpl"
                			fieldConversions="principalId:changeTargetPrincipalId"
                			lookupParameters="" 
                			baseLookupUrl="./kr/"/>
						<input type="button" class="button" value="Submit" name="changeEmployee" onclick="this.form.methodToCall.value='changeEmployee'; this.form.submit();">
						<input type="button" class="button" value="Clear" name="clearChangeEmployee" onclick="this.form.methodToCall.value='clearChangeEmployee'; this.form.submit();">
	                </td>
	            </tr>	   
	            <tr/>
	            <c:if test="${Form.user.systemAdmin}">
	            <tr class="header"><td><b>Delete Timesheet</b></td></tr>
	            <tr>
	                <td>
						<html:text property="deleteDocumentId" size="20" />
						<input type="button" class="button" value="Submit" name="deleteTimesheet" onclick="this.form.methodToCall.value='deleteTimesheet'; this.form.submit();">
	                </td>
	            </tr>	                     
	            </c:if>
		  </table>
	  </html:form>
    </div>
  </c:if>
</div>
</tk:tkHeader>