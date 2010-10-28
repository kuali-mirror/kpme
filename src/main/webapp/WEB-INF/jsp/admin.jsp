<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${AdminActionForm}" scope="request"/>

<tk:tkHeader tabId="admin">
<div id="admin">
	<fieldset style="width:50%">
	    <legend><b>Maintenance pages</b></legend>
	    
		<table>
		    <tr>
		       <td><b>Rules</b></td>
		       <td><b>Administrative Activities</b></td>
		    </tr>
			<tr>
				<td>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clock.location.ClockLocationRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Clock Location Rule</a></li>   
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.dept.lunch.DeptLunchRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Department Lunch Location Rule</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.graceperiod.rule.GracePeriodRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Grace Period Rule</a></li>   
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.collection.rule.TimeCollectionRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">TimeCollection Rule</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Shift Differential Rule</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Weekly OverTime Rule</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">DailyOverTimeRule</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.TimeCollectionRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Time Collection Rule</a></li>
				</td>
				
				<td>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.department.Department&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Department</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.AccrualCategory&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Accrual Category</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.TimeOffAccrual&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Time Off Accrual</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.holidaycalendar.HolidayCalendar&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Holiday Calendar</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paytype.PayType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Pay Type</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earncode.EarnGroup&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Earn Group</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.assignment.Assignment&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Assignment</a></li>					
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workschedule.WorkSchedule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">WorkSchedule</a></li>						
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.dept.earncode.DepartmentEarnCode&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Department Earn Code</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clocklog.ClockLog&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Clock Log</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Work Area Maintenance</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Account&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Account</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Chart&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Chart</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.ObjectCode&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Object Code</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.Organization&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Organization</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.ProjectCode&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Project Code</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.SubAccount&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Sub Account</a></li>
					<li><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.coa.businessobject.SubObjectCode&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Sub Object Code</a></li>
				</td>
			</tr>
		</table>
	</fieldset>
	
	<html:form action="/Admin" method="post">
		<html:hidden property="methodToCall" value=""/>
		
		
		<fieldset style="width:50%">
		    <legend><b>Backdoor</b></legend>
				<html:text property="backdoorPrincipalName" size="20" />
				<input type="button" class="button" value="Submit" name="backdoor" onclick="this.form.methodToCall.value='backdoor'; this.form.submit();">
				<input type="button" class="button" value="Clear" name="clearBackdoor" onclick="this.form.methodToCall.value='clearBackdoor'; this.form.submit();">
		</fieldset>
	</html:form>
</div>
</tk:tkHeader>