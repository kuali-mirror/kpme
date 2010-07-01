<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/jsp/tlds.jsp"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
    //	javascript functions for use with the CardReaderApplet
    // cardId is on track2 -- that's all we care about.
    function processCardData(t1,t2,t3){
      t2 = stripTerminators(t2);
      cardId = getCardId(t2);
    
      setFormField('KioskLoginForm','cardId',cardId);
      document.KioskLoginForm.submit();
    }
    
    // make sure to get only one occurrence of each track
    // the data for each track ends with ?
    function stripTerminators(string) {
      var termIndex = string.indexOf('?');
      var retString = string.substring(1,termIndex);
      return retString;
    }
    
    // Parse track2 to get cardId.
    //   At IU-B, track2 format is cardId=#####...
    //   At IUPUI, track2 is the cardId (w/o other info).
    // If an equal sign is present, return string prior to it (IU-B case).
    // Otherwise, return the entire string (IUPUI case)
    function getCardId(string) {
      var separatorIndex = string.indexOf('=');
      if (separatorIndex < 0) {
        return string;
      } else {
        var retString = string.substring(0,separatorIndex);
        return retString;
      }
    }
    
    function setFormField(formName, fieldName, newValue) {
      eval("document."+formName+"."+fieldName+".value"+"='"+newValue+"'");
      return true;
    }
</script>

<script>
  // JavaScript clock.  Displays server time, not client time.
  
  // Determine offset between server & client clocks
  <jsp:useBean id="serverTime" class="java.util.Date"/>
  var serverTime = new Date();
  serverTime.setTime(<%= serverTime.getTime() %>);
  var clientTime = new Date();
  var offsetMilliseconds = serverTime.getTime() - clientTime.getTime();
  
  var dayNames=new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday');
  var monthNames=new Array('January','February','March','April','May','June','July','August','September','October','November','December');
 
  function displayServerTime() {
     var clientNow = new Date();    
     var serverNow = new Date(clientNow.getTime() + offsetMilliseconds);
     document.getElementById('clock').innerHTML=
                     dayNames[serverNow.getDay()]+' '
                    +monthNames[serverNow.getMonth()]+' '
                    +leadingZero(serverNow.getDate())+', '
                    +fixYear4(serverNow.getYear())+' '
                    +twelveHour(serverNow.getHours())+':'
                    +leadingZero(serverNow.getMinutes())+':'
                    +leadingZero(serverNow.getSeconds())+' '
                    +amPMsymbol(serverNow.getHours());  
     setTimeout('displayServerTime()',1000); 
  }
  function leadingZero(x){
     return (x>9)?x:'0'+x;
  }
  function twelveHour(x){
     if(x==0){
        x=12;
     }
     return (x>12)?x-=12:x;  
  }
  function amPMsymbol(x){
     return (x>11)?'PM':'AM';  
  }
  function fixYear4(x){
     return (x<500)?x+1900:x;
  }
</script>
</head>

<BODY onLoad="displayServerTime()">
  <table align="center">
   <tr>   
     <td align="center">
        <b><span id="clock">&nbsp;</span></b>
        <form name="KioskLoginForm" method="post" action="../../TimesheetDocument.do?method=open">
          <INPUT TYPE="HIDDEN" NAME="isKioskUser" VALUE="y">
          <INPUT TYPE="HIDDEN" NAME="cardId"      VALUE="">
         </form>    
      </td> 
    </tr>
    
    <tr>    
        <td align="center">
          <img src="../../jsp/images/time_cardSwipe.gif" border="0" 
               width=224 height=150 hspace=20 vspace=20 
               alt="Card Swipe Picture">
        </td>        
   </tr>
   
    <tr>
       <td align="center">
          <form name="CASLogin" method="post" action="../../TimesheetDocument.do?method=open">
             <INPUT TYPE=SUBMIT NAME="CASLogin" VALUE="CAS Login">
             
          </form>
      </td>
    </tr>
    
   </table>

   <applet name="CardReader"
        code="edu.iu.uis.hr.tk.kiosk.client.cardReader.applet.CardReaderApplet.class"
        archive="<%=request.getContextPath()%>/applets/tk_CardReader.jar"
        width="1"
        height="1"
        mayscript>
</applet>
   
</body>
</html>