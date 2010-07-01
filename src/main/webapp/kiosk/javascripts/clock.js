  var serverTime = new Date();
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
                    +fixYear4(serverNow.getYear())+' <br>'
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