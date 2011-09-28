<html>
<head>
<link href="kr/css/kuali.css" rel="stylesheet" type="text/css" />
</head>
<title> TIME - Session Expired</title>
<body>

<div id="headerarea" class="headerarea">
<span class="left">
<img border="0" alt="Logo" src="jsp/images/time_logo3.gif"/>
</span>
</div>

<br />
<br />

<div align="center">
<table width="95%" cellpadding="0" cellspacing="0"
	style="border-style: ridge; border-color: #7d1100; border-width: thin">
	<tr>
		<td style="background-color: #F0F0F0">

		<table width="100%">
			<tr>
				<td width="33%" valign="top">
				<div class="portlet">
				<div class="header">
				<h2>Your session has expired.</h2>
				</div>

				<div class="portlet-content">
				<hr>
				<center>
				<h5> For security reasons, your session expires after 30 minutes of inactivity. <BR><BR> 
				   Please <a href="#">click</a> here to access the TIME system.
				</h5>
				<c:if test="${not empty stacktrace }">
					<hr />
					<pre
						style="font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif;">${stacktrace}</font></pre>
				</c:if></div>
				</td>
			</tr>
		</table>
		<br>
		<br>
		</td>
	</tr>
</table>
</body>

</html>