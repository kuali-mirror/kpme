<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${TimeDetailActionForm}" scope="request"/>

<tk:tkHeader tabId="timeDetail">
	<html:hidden property="methodToCall" value=""/>
	<html:hidden property="beginPeriodDate" value="${Form.beginPeriodDate}" styleId="beginPeriodDate"/>
	<html:hidden property="endPeriodDate" value="${Form.endPeriodDate}" styleId="endPeriodDate"/>

	<div style="clear:both;" class="ui-widget-content">
		<%--
		<div class="ui-widget" id="timesheet-panel">
			<table>
				<tr>
					<td colspan="2"><span class="timesheet-panel-title">Add time blocks :</span><br/></td>
				</tr>
				<tr>
					<td>Clocked In:</td>
					<td>
						<input name="beginTime" id="beginTimeField" type="text" size="10" onblur="magicTime(this)" onfocus="if (this.className != 'error') this.select()"/>
						 <button tabindex="-1" style="width:20px; height:20px; vertical-align: text-top"
						 title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530"
						 id="beginTimeHelp">help</button>
						<div id="beginTimeField-messages"></div>
					</td>
				</tr>
				<tr>
					<td>Clocked Out:</td>
					<td>
						<input name="endTime" id="endTimeField" type="text" size="10" onblur="magicTime(this)" onfocus="if (this.className != 'error') this.select()"/>
						<button style="width:20px; height:20px; vertical-align: text-top" id="endTimeHelp"
						title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530">help</button>
						<div id="endTimeField-messages"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type="checkbox"/> Apply time to each day</td>
					<td></td>
				</tr>
				<tr>
					<td>Begin Date:</td>
					<td><input type="text" id="timesheet-beginDate" size="10"/></td>
				</tr>
				<tr>
					<td>End Date:</td>
					<td><input type="text" id="timesheet-endDate" size="10"/></td>
				</tr>

				<tr>
					<td>Assignment: </td>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<select>
							<option>Operations/AIS</option>
							<option>AIS Technical & User Support</option>
							<option>System Support Services</option>
						</select>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>Earn code: </td>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<tk:earnCode/>
					</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" class="button" value="Submit" name="Submit"></td>
				</tr>
			</table>
		</div>
		--%>

		<%--This is for visually impaired users --%>
		<!--
		<c:forEach var="timeBlock" items="${Form.timeBlockList}" varStatus="row">
			Document Id: ${timeBlock.documentId}<br/>
			Job Number: ${timeBlock.jobNumber}<br/>
			Workarea Id: ${timeBlock.workArea}<br/>
			Task Id: ${timeBlock.task}<br/>
			Earn Code: ${timeBlock.earnCode}<br/>
			Begin Time: <fmt:formatDate type="both" dateStyle="full" value="${timeBlock.beginTimestamp}"/><br/>
			End Time: <fmt:formatDate type="both" dateStyle="full" value="${timeBlock.endTimestamp}"/><br/>
			Hours: ${timeBlock.hours}<br/>
			Amount: ${timeBlock.amount}<br/>
			<br/>
		</c:forEach>
		 -->
		<div class="global-error">Error: This is a global error for the demo purpose</div>
		<div id="cal" style="margin-top: 20px; width:100%; font-size:.9em;"/>

				<div id="dialog-form" title="Add time blocks:">
					<p class="validateTips">All form fields are required.</p>

					<form>
						<div class="ui-widget" id="timesheet-panel">
							<table>
								<tr>
									<td>Date range:</td>
									<td><input type="text" id="date-range-begin" size="10"/> - <input type="text" id="date-range-end" size="10"/></td>
								</tr>
								<tr>
									<td>Assignment: </td>
									<td>
										<tk:assignment assignments="${Form.assignmentDescriptions}"/>
									</td>
								</tr>
								<tr>
									<td>Earn code: </td>
									<td>
										<!-- <tk:earnCode earnCodes="${Form.earnCodeDescriptions}"/> -->
										<select id='earnCode' name="selectedEarnCode">
											<option value=''>-- select an assignment --</option>
										</select>
									</td>
								</tr>
								<tr id="clockIn">
									<td><span style="float:right;">In:</span></td>
									<td>
										<input name="beginTimeField" id="beginTimeField" type="text" size="10" onblur="magicTime(this)" onfocus="if (this.className != 'error') this.select()"/>
										 <button tabindex="-1" style="width:20px; height:20px; vertical-align: text-top"
										 title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530"
										 id="beginTimeHelp">help</button>
										<div id="beginTimeField-messages" style="display:none;"></div>
									</td>
								</tr>
								<tr id="clockOut">
									<td><span style="float:right;">Out:</span></td>
									<td>
										<input name="endTimeField" id="endTimeField" type="text" size="10" onblur="magicTime(this)" onfocus="if (this.className != 'error') this.select()"/>
										<button style="width:20px; height:20px; vertical-align: text-top" id="endTimeHelp"
										title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530">help</button>
										<div id="endTimeField-messages" style="display:none;"></div>
									</td>
								</tr>
								<tr id="hours" style="display:none;">
									<td>Hours:</td>
									<td>
										<input id="hoursField" name="hours"/>
									</td>
								</tr>
								<tr>
									<td></td>
									<td><input type="checkbox" id="acrossDays"/> Apply time to each day</td>
								</tr>
								<%--
								<tr>
									<td>Begin Date:</td>
									<td><input type="text" id="timesheet-beginDate" size="10"/></td>
								</tr>
								<tr>
									<td>End Date:</td>
									<td><input type="text" id="timesheet-endDate" size="10"/></td>
								</tr>
								 --%>
							</table>
						</div>
					</form>
				</div> <%-- end of dialog-form --%>
		<%--<div style="width:100%; font-size: .8em; text-align: center; margin-top: 10px;"><input type="submit" class="button" name="submit" value="Save"/></div>  --%>

		<%--
		<div id="tabs-demo">
			<ul>
				<li><a href="#tabs-1">Demo - tabular view</a></li>
				<li><a href="#tabs-2">Demo - calendar view</a></li>
			</ul>
			<div id="tabs-1">
				<form id="timesheetForm" method="post" action="#">
					<div id="timesheet">
						<!-- <div class="toolbar-timesheet-table-week1"></div>  -->
						<table class="timesheet-table-week1">
							<thead>
								<tr>
									<th></th>
									<th><bean:message key="time.detail.assignment"/></th>
									<th><bean:message key="time.detail.earnCode"/></th>
									<th><bean:message key="time.detail.action"/></th>
									<th>4/1 Sun</th>
									<th>4/2 Mon</th>
									<th>4/3 Tue</th>
									<th>4/4 Wed</th>
									<th>4/5 Thu</th>
									<th>4/6 Fri</th>
									<th>4/7 Sat</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td>
										<select>
											<option>HRMS Java Developer</option>
											<option>HRMS PS Developer</option>
										</select>
									</td>
									<td><tk:earnCode/></td>
									<td>Time In <br/> Time Out</td>
									<td><input type="text" size="8" name="1"/><br/><input type="text" size="8" name="2"/></td>
									<td><input type="text" size="8" name="3"/><br/><input type="text" size="8" name="4"/></td>
									<td><input type="text" size="8" name="5"/><br/><input type="text" size="8" name="6"/></td>
									<td><input type="text" size="8" name="7"/><br/><input type="text" size="8" name="8"/></td>
									<td><input type="text" size="8" name="9"/><br/><input type="text" size="8" name="10"/></td>
									<td><input type="text" size="8" name="11"><br/><input type="text" size="8" name="12"/></td>
									<td><input type="text" size="8" name="13"><br/><input type="text" size="8" name="14"/></td>
								</tr>
							</tbody>
						</table>

						<!-- <div class="toolbar-timesheet-table-week2"></div>  -->
						<table class="timesheet-table-week2">
							<thead>
								<tr>
									<th></th>
									<th>Account Name</th>
									<th>Earn Code</th>
									<th>Action</th>
									<th>4/8 Sun</th>
									<th>4/9 Mon</th>
									<th>4/10 Tue</th>
									<th>4/11 Wed</th>
									<th>4/12 Thu</th>
									<th>4/13 Fri</th>
									<th>4/14 Sat</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td>
										<select>
											<option>HRMS Java Developer</option>
											<option>HRMS PS Developer</option>
										</select>
									</td>
									<td><tk:earnCode/></td>
									<td>Time In <br/> Time Out</td>
									<td><input type="text" size="8" name="1"/><br/><input type="text" size="8" name="2"/></td>
									<td><input type="text" size="8" name="3"/><br/><input type="text" size="8" name="4"/></td>
									<td><input type="text" size="8" name="5"/><br/><input type="text" size="8" name="6"/></td>
									<td><input type="text" size="8" name="7"/><br/><input type="text" size="8" name="8"/></td>
									<td><input type="text" size="8" name="9"/><br/><input type="text" size="8" name="10"/></td>
									<td><input type="text" size="8" name="11"><br/><input type="text" size="8" name="12"/></td>
									<td><input type="text" size="8" name="13"><br/><input type="text" size="8" name="14"/></td>
								</tr>
							</tbody>
						</table>
						<div style="width:100%; font-size: .8em; text-align: center; margin-top: 10px;"><input type="submit" class="button" name="submit" value="Save"/></div>
					</div>
				</form>
			</div>

			<div id="tabs-2">

			</div> <%-- end of tabs-2 --%>
		<%-- </div> end of tabs-demo --%>

		<tk:timeSummary timeSummary="${Form.timesheetDocument.timeSummary}" />

		<%--
		<div id="timesheet-summary">
			<div style="clear:both; text-align:center; font-weight: bold; margin-top:20px; margin-bottom: 5px;">Summary (<a href="#" id="basic">Basic</a> / <a href="#" id="advance">Advanced</a> )</div>
			<div id="timesheet-table-basic">

			<table style="width:100%;">
				<thead>
					<tr class="ui-state-default">
						<td></td>
						<td>5/30 Sun</td>
						<td>5/31 Mon</td>
						<td>6/1 Tue</td>
						<td>6/2 Wed</td>
						<td>6/3 Thu</td>
						<td>6/4 Fri</td>
						<td>6/5 Sat</td>
						<td>Weekly Total</td>
						<td>6/6 Sun</td>
						<td>6/7 Mon</td>
						<td>6/8 Tue</td>
						<td>6/9 Wed</td>
						<td>6/10 Thu</td>
						<td>6/11 Fri</td>
						<td>6/12 Sat</td>
						<td>Weekly Total</td>
						<td>Period Total</td>
					</tr>
				</thead>
				<tbody>
					<tr style="border-bottom-style: double; font-weight: bold;">
						<td>Worked Hours:</td>
						<td>4.00</td>
						<td></td>
						<td></td>
						<td>4.00</td>
						<td></td>
						<td></td>
						<td>2.00</td>
						<td style="background-color: #CFCFCF; font-weight: bold;">10.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>2.00</td>
						<td style="background-color: #CFCFCF">22.00</td>
						<td style="background-color: #CFCFCF">32.00</td>
					</tr>
					<tr>
						<td colspan="18" style="text-align:left; font-weight: bold;">RGH: Regular Pay Hourly</td>
					</tr>
					<tr style="">
						<td>HRMS Java Developer:</td>
						<td>4.00</td>
						<td></td>
						<td></td>
						<td>4.00</td>
						<td></td>
						<td></td>
						<td></td>
						<td style="background-color: #CFCFCF;font-weight: bold;">8:00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td></td>
						<td style="background-color: #CFCFCF; font-weight: bold;">20.00</td>
						<td style="background-color: #CFCFCF; font-weight: bold;">28.00</td>
					</tr>
					<tr style="font-weight:bold; border-bottom-style: double;">
						<td>Regular Pay Hours:</td>
						<td>4.00</td>
						<td></td>
						<td></td>
						<td>4.00</td>
						<td></td>
						<td></td>
						<td></td>
						<td style="background-color: #CFCFCF">8.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td>4.00</td>
						<td></td>
						<td style="background-color: #CFCFCF">20.00</td>
						<td style="background-color: #CFCFCF">28.00</td>
					</tr>
					<tr>
						<td colspan="18" style="text-align:left; font-weight: bold;">OVT: Overtime</td>
					</tr>
					<tr >
						<td>HRMS Java Developer:</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>2.00</td>
						<td style="background-color: #CFCFCF; font-weight: bold">2.00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>2.00</td>
						<td style="background-color: #CFCFCF; font-weight: bold;">2.00</td>
						<td style="background-color: #CFCFCF; font-weight: bold;">4.00</td>
					</tr>
					<tr style="font-weight:bold; border-bottom-style: double;">
						<td>Overtime:</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>2.00</td>
						<td style="background-color: #CFCFCF">2.00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>2.00</td>
						<td style="background-color: #CFCFCF">2.00</td>
						<td style="background-color: #CFCFCF">4.00</td>
					</tr>
				</tbody>
			</table>

			<%--
			<table style="width:100%;">
				<thead>
					<tr class="ui-state-default">
						<td colspan="2">Hours / Assignments</td>
						<td>5/30 Sun</td>
						<td>5/31 Mon</td>
						<td>6/1 Tue</td>
						<td>6/2 Wed</td>
						<td>6/3 Thu</td>
						<td>6/4 Fri</td>
						<td>6/5 Sat</td>
						<td>6/6 Sun</td>
						<td>6/7 Mon</td>
						<td>6/8 Tue</td>
						<td>6/9 Wed</td>
						<td>6/10 Thu</td>
						<td>6/11 Fri</td>
						<td>6/12 Sat</td>

					</tr>
				</thead>
				<tbody>
					<tr>
						<td rowspan="2" style="vertical-align: middle;">Hours</td>
						<td>HRMS Java developer</td>
						<td></td>
						<td></td>
						<td></td>
						<td>8 RGN</td>
						<td></td>
						<td>4 VAC</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>HRMS PS developer</td>
						<td></td>
						<td></td>
						<td></td>
						<td>8 RGN</td>
						<td></td>
						<td>4 VAC</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr style="font-weight:bold;">
						<td rowspan="2" style="vertical-align: middle;">Total</td>
						<td>HRMS Java developer</td>
						<td colspan="14">8 RGN, 4 VAC</td>
					</tr>
					<tr style="font-weight:bold;">
						<td>HRMS PS developer</td>
						<td colspan="14">8 RGN, 4 VAC</td>
					</tr>
				</tbody>
			</table>
			</div>

			<div id="timesheet-table-advance" style="display:none;">
			<table style="width:100%;">
				<thead>
					<tr class="ui-state-default">
						<td colspan="2">Hours / Assignments</td>
						<td>5/30 Sun</td>
						<td>5/31 Mon</td>
						<td>6/1 Tue</td>
						<td>6/2 Wed</td>
						<td>6/3 Thu</td>
						<td>6/4 Fri</td>
						<td>6/5 Sat</td>
						<td>6/6 Sun</td>
						<td>6/7 Mon</td>
						<td>6/8 Tue</td>
						<td>6/9 Wed</td>
						<td>6/10 Thu</td>
						<td>6/11 Fri</td>
						<td>6/12 Sat</td>

					</tr>
				</thead>
				<tbody>
					<tr>
						<td rowspan="2" style="vertical-align: middle;">Hours</td>
						<td>HRMS Java developer</td>
						<td></td>
						<td></td>
						<td></td>
						<td>In:08:00am - Out:12:00pm<br/>Lunch:12:00pm - 1:00pm</td>
						<td></td>
						<td>4 VAC</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>HRMS PS developer</td>
						<td></td>
						<td></td>
						<td></td>
						<td>In:08:00am - Out:12:00pm</td>
						<td></td>
						<td>4 VAC</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr style="font-weight:bold;">
						<td rowspan="2" style="vertical-align: middle;">Total</td>
						<td>HRMS Java developer</td>
						<td colspan="14">8 RGN, 4 VAC</td>
					</tr>
					<tr style="font-weight:bold;">
						<td>HRMS PS developer</td>
						<td colspan="14">8 RGN, 4 VAC</td>
					</tr>
				</tbody>
			</table>
			--%>
			</div>
		</div>
	</div>
</tk:tkHeader>