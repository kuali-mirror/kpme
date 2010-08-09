<%@ include file="/jsp/tlds.jsp" %>

<html>
<head>
	<title>TK Index</title>
	<link href="kr/css/kuali.css" rel="stylesheet" type="text/css" />
	<link type="text/css" href="../jsp/css/hr_styles.css" rel="stylesheet">
</head>
<body>
<div id="headerarea" class="headerarea">
<span class="left">
<img border="0" alt="Logo" src="jsp/images/time_logo3.gif"/>
</span>

<span class="right" style="margin-right: 10px;">

</span>
</div>

<br/><br/>

 <div align="center" id="main">
 <table width="95%" cellpadding="0" cellspacing="0" style="border-style:ridge; border-color:#7d1100; border-width:thin">
   <tr>
    <td style="background-color:#F0F0F0">
    
      <table width="100%">
        <tr>
        <td width="33%" valign="top">
		   <div class="portlet">
	          <div class="header">
	    		<h2>Maintenance</h2>
	          </div>
			  <div class="portlet-content">
			
					     <a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clock.location.ClockLocationRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Clock Location Rule</a>   
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.department.Department&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Department Maintenance</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.dept.lunch.DeptLunchRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Department Lunch Location Rule</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.graceperiod.rule.GracePeriodRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Grace Period Rule</a>   
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earncode.EarnCode&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Earn Code</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.AccrualCategory&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Accrual Category</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.TimeOffAccrual&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Time Off Accrual</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Holiday Calendar Date</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.holidaycalendar.HolidayCalendar&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Holiday Calendar</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.salgroup.SalGroup&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Sal Group</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paytype.PayType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Pay Type</a>
					<br/><a href="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.graceperiod.rule.GracePeriodRule&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888">Grace Period Rule</a>
			   </div>    
           </div>
           <div class="portlet">
              <div class="header">
                  <h2>Inquiry</h2>
              </div>
              <div class="portlet-content">
				
 				
              </div>
           </div>
        </td>
        <td width="33%" valign="top">
           <div class="portlet">
			  <div class="header">
				  <h2>Support</h2>
			  </div>
			  <div class="portlet-content">
				
				
			  </div>
		   </div>        
		   <div class="portlet">
	          <div class="header">
	              <h2>OneStart Portal</h2>
	          </div>
		 	  <div class="portlet-content">
			  </div>
           </div>
           <div class="portlet">
	          <div class="header">
	              <h2>Kiosk</h2> 
	          </div>
			  <div class="portlet-content">
			  </div>
           </div>
	       <div class="portlet">
	          <div class="header">
	              <h2>Administrative</h2> 
	          </div>
			  <div class="portlet-content">
			  	

				<form name="backdoorForm">
				   BackdoorId <input type="text" name="backdoorId">

				</form>
			   </div>
	       </div>
        </td>
        </tr>
      </table> 
    <br/><br/>
    </td>
   </tr>
 </table>
<br/><br/>
</body>
</html>