<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" xmlns:app="http://www.appcelerator.org">
<head>
<title>TIME Kiosk Login</title>
<link type="text/css" href="../jsp/css/hr_styles.css" rel="stylesheet">
    <script src="javascripts/appcelerator.js" type="text/javascript"></script>
    <script src="javascripts/clock.js" type="text/javascript"></script>
	<SCRIPT LANGUAGE="VBScript">
<!--
	Sub window_onLoad()
			document.KioskLoginForm.txtCommPort.value = PortPowerSwipe.GetDefSetting("CommPort")
			document.KioskLoginForm.txtSettings.value = PortPowerSwipe.GetDefSetting("Settings")
			
			if(document.KioskLoginForm.txtCommPort.value <> "")then
				PortPowerSwipe.CommPort=CInt(document.KioskLoginForm.txtCommPort.value)
			else 
				PortPowerSwipe.CommPort=1
			end if
			if(document.KioskLoginForm.txtCommPort.value = "")then
				document.KioskLoginForm.txtCommPort.value="1"
			end if
			if(document.KioskLoginForm.txtSettings.value = "")then
				document.KioskLoginForm.txtSettings.value="9600,n,8,1"
			end if
			PortPowerSwipe.PortOpen = True
			If Not(PortPowerSwipe.PortOpen)  then
			  MsgBox "Unable to Open COM" & PortPowerSwipe.CommPort
			End if
	end sub
	
	Sub window_onUnload()
		PortPowerSwipe.SaveDefSetting "CommPort",document.KioskLoginForm.txtCommPort.value
		PortPowerSwipe.SaveDefSetting "Settings",document.KioskLoginForm.txtSettings.value
		PortPowerSwipe.PortOpen = False
	end sub
	
	Sub PortPowerSwipe_CardDataChanged()
		Dim Status
        Dim CardId
		CardId=PortPowerSwipe.FindElement(2, ";", 0, "=")
        If(Len(CardId) = 0) Then
            document.KioskLoginForm.cardId.value=PortPowerSwipe.FindElement(2, ";", 0, "?")
        Else
            document.KioskLoginForm.cardId.value=CardId
        End If
        if(Len(document.KioskLoginForm.cardId.value) = 0) then
            MsgBox "Unable to read card"
        End if
		PortPowerSwipe.ClearBuffer()
	end sub
	
	Sub cmdClear_Click()
		document.KioskLoginForm.cardId.value=""
	end sub
-->
	</SCRIPT>
	
	<script language="JavaScript">
	if (!Appcelerator.Browser.isIE) {
		$MQ("l:isNotIE",{"message":"In order to swipe your identification card, you must be using Internet Explorer.  You may proceed with a CAS login."});
	}
	</script>
</head>
<body>
<div id="headerarea" class="headerarea">
<span class="left">
<img border="0" alt="Logo" src="../jsp/images/time_logo3.gif"/>
</span>
<span class="right" style="margin-right: 10px;" > <br><br>
<a href="javascript:parent.window.location.reload()">refresh window</a>
</span>
</div>
<OBJECT ID="PortPowerSwipe"
CLASSID="CLSID:3DBA7D52-CA8B-4301-8D57-01EA4F799E97"
CODEBASE="card_reader_activex.CAB#version=1,4,0,5">
</OBJECT>
<br> <div align="center">
<br/><br/>
<span id="clock" style="font-size: 24pt; color: silver">&nbsp;</span>



<script language="JavaScript">
 displayServerTime();
</script>
         
         
<br>
 	<form name="KioskLoginForm" method="post" action="../TimesheetDocument.do?method=open" target="_self" on="r:validation.response[validid=true] then submit">
		<input type="hidden" name="kioskUser" value="1">
		<input type="hidden" name="liteMode" value="1">
		<input type="hidden" id="cardId" name="cardId" on="change then r:validation.request[cardid=$cardId]" value="-1">
	    <input type="hidden" name="txtCommPort">
	    <input type="hidden" name="txtSettings">
	  </form>
<div on="l:isNotIE then value[message]">
	<div>
	    	<span on="r:validation.response[validid=false] then hide or r:validation.request then show" style="display:none;"><img src="../jsp/images/processingIndicator.gif"></span>
	    	<br>
	    	<span id="cardIdMessage" on="r:validation.response then value[message] or r:validation.request then value[&nbsp;]" style="font-size: 12px;">Please swipe your Campus ID Card</span>
	<br/><br/>
	  	    <img src="../jsp/images/cardswipe.jpg" />
	</div>  
</div>
	<br/><br/>	    
<div>
			<span style="font-size:12px"> Login here if you do not have a Campus ID Card <br> or if you prefer to use Username and Passphrase</span>  <br><br>
			 <form name="CASLogin" method="post" action="../TimesheetDocument.do" target="_self">
				<input type="hidden" name="method" value="open">
				<input type="hidden" name="kioskUser" value="1">
				<input type="hidden" name="liteMode" value="1">
                <input type="image" src="../jsp/images/buttonsmall_login.gif"  align="middle" border="0" width="100" style="border:0" height="30" alt="CAS Login">
             </form>
 </div>


<div id="stacktrace" on="r:validation.response[exception=true] then value[stacktrace]" style="color: white;"></div>					

<IFRAME src="https://cas.iu.edu/cas/logout" frameborder="0" scrolling="no" height=0 width=0 style="visible:no">

</body>

</html>